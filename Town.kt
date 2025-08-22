import kotlin.random.Random

/**
 * Represents a town in the pirate game that can be visited and looted by the pirate.
 * Towns contain treasure, obstacles, and enemies.
 *
 * @property name The name of the town.
 * @property treasureAmount The total amount of treasure available in the town.
 * @property minTreasureToLoot The minimum amount of treasure the pirate needs to collect
 * for the town to be considered sufficiently looted.
 * @property obstacles A list of [Obstacle]s present in the town.
 * @property enemies A list of [Enemy] types that the pirate might encounter in this town.
 */
class Town(
    val name: String,
    val treasureAmount: Int,
    val minTreasureToLoot: Int,
    private val obstacles: List<Obstacle>,
    private val enemies: List<Enemy>
) {

    private var lootedTreasure = 0
    /**
     * Indicates whether the town has been looted sufficiently by the pirate.
     * True if the [lootedTreasure] meets or exceeds [minTreasureToLoot].
     */
    val isLootedSufficiently: Boolean
        get() = lootedTreasure >= minTreasureToLoot

    /**
     * Simulates the pirate's attempt to loot the town.
     * The pirate will first encounter obstacles, then battle enemies.
     * If the pirate survives, they will find treasure and potentially a hidden stash
     * containing health potions, new weapons, or extra gold.
     *
     * @param pirate The [Pirate] attempting to loot the town.
     */
    fun loot(pirate: Pirate) {

        println("\n--- Sailing to the town of $name ---")
        println("This town is rumored to hold $treasureAmount gold pieces.")
        println(
            "You must collect at least $minTreasureToLoot to consider it " +
                    "properly looted."
        )
        // Record that the pirate has visited this town.
        pirate.visitedTowns.add(name)

        // Pirate must first overcome any obstacles in the town.
        for (obstacle in obstacles) {
            if (!pirate.isAlive()) break // Stop if the pirate is defeated by an obstacle.
            obstacle.trigger(pirate)
        }

        // If pirate is defeated by obstacles, the looting ends here.
        if (!pirate.isAlive()) {
            println("You succumbed to the dangers of $name...")
            return
        }

        // Create a mutable list of enemies for this encounter.
        // This allows enemies to be removed from the list as they are defeated.
        // It also ensures that each visit to the town spawns a fresh set of enemies based on the template.
        var currentEnemies = enemies.map {
            when (it) {
                is Mercenary -> Mercenary(
                    it.name, it.health,
                    it.damage, it.accuracy
                )

                is Soldier -> Soldier(
                    it.name, it.health,
                    it.damage, it.accuracy
                )

                else -> Enemy(
                    it.name, it.health,
                    it.damage, it.accuracy
                )
            }
        }

        // Battle sequence: pirate fights enemies one by one.
        while (currentEnemies.isNotEmpty() && pirate.isAlive()) {
            val enemy = currentEnemies.first()
            println("\nA wild ${enemy.name} appears! (Health: ${enemy.health})")
            battle(pirate, enemy) // Engage in battle with the current enemy.
            if (pirate.isAlive()) {
                // If pirate survives, the enemy is defeated.
                println("You defeated the ${enemy.name}!")
                currentEnemies = currentEnemies.drop(1) // Remove defeated enemy from the list.
            }
        }

        // After all obstacles and enemies are dealt with (if pirate survives).
        if (pirate.isAlive()) {
            // Determine the amount of treasure found.
            val foundTreasure = Random.nextInt(
                minTreasureToLoot, // Pirate finds at least the minimum required.
                treasureAmount + 1 // Max treasure, +1 because nextInt's upper bound is exclusive.
            )
            lootedTreasure = foundTreasure
            pirate.treasure += foundTreasure
            println("You looted $foundTreasure gold pieces from $name!")
            println("Your total treasure is now ${pirate.treasure} gold pieces.")


            // Chance to find a hidden stash for an extra bonus.
            if (Random.nextDouble() < 0.4) { // 40% chance to find a hidden stash.
                println("You found a hidden stash!")

                when (Random.nextInt(1, 4)) { // Determine the type of hidden stash (1 to 3).
                    1 -> {
                        println("It's a health potion! You feel invigorated.")
                        pirate.heal(30)
                    }

                    2 -> {
                        val newWeapon = Weapon.entries.random() // Find a random weapon.
                        if (!pirate.weapons.contains(newWeapon)) {
                            pirate.acquireWeapon(newWeapon)
                        } else {
                            println(
                                "You already have a ${newWeapon.name.lowercase()}!" +
                                        " You can't acquire it again."
                            )
                        }
                    }

                    3 -> {
                        val extraTreasure = Random.nextInt(50, 101) // Find extra gold (50-100 pieces).
                        println("It's a bag of gold! You found $extraTreasure gold pieces!")
                        pirate.treasure += extraTreasure
                        println("Your total treasure is now ${pirate.treasure} gold pieces.")
                    }
                }
            }
        }
        else {
            // If the pirate was defeated during the battles.
            println("You were defeated in battle by the forces in $name...")
        }
    }

    /**
     * Simulates a battle between the pirate and an enemy.
     * The battle continues in turns until either the pirate or the enemy is defeated,
     * or the pirate successfully flees.
     *
     * @param pirate The [Pirate] engaged in battle.
     * @param enemy The [Enemy] the pirate is fighting.
     */
    private fun battle(pirate: Pirate, enemy: Enemy) {

        while (pirate.isAlive() && enemy.isAlive()) {
            println("\n--- Battle ---")
            println("Your health: ${pirate.health}")
            println("${enemy.name}'s health: ${enemy.health}")

            // Pirate gets to choose/confirm their weapon at the start of their turn.
            pirate.changeWeapon()
            println("\nYour turn! What do you do?")
            println("1. Attack with ${pirate.currentWeapon.name.lowercase()}")
            println("2. Try to flee")
            print("Enter your choice: ")

            when (readlnOrNull()) {
                "1" -> { // Pirate chooses to attack.
                    if (Random.nextDouble() < pirate.currentWeapon.accuracy) {
                        val damageDealt = pirate.currentWeapon.damage
                        enemy.takeDamage(damageDealt)
                        println(
                            "You dealt $damageDealt damage to the ${enemy.name}!" +
                                    "\n${enemy.name}'s health is now ${enemy.health}."
                        )
                    }
                    else {
                        println("Your attack missed!")
                    }
                }
                "2" -> { // Pirate tries to flee.
                    if (Random.nextDouble() < 0.4) { // 40% chance to successfully flee.
                        println("You successfully fled from the battle!")
                        return // Exit battle method immediately.
                    }
                    else {
                        println("You failed to flee!")
                    }
                }
                else -> {
                    println("Invalid choice. You hesitate and do nothing.")
                }
            }
            // If enemy is defeated after pirate's attack, enemy doesn't get to attack.
            if (!enemy.isAlive()) {
                continue // Skip to the next iteration of the while loop (effectively ending battle if enemy was last).
            }

            // Enemy's turn to attack, if still alive.
            enemy.attack(pirate)
        }
    }

}
