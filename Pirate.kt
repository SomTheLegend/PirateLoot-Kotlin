/**
 * Represents the player's character, the Pirate.
 * Manages health, treasure, weapons, and game state.
 *
 * @property name The name of the pirate.
 * @property health The current health of the pirate.
 * @property currentWeapon The weapon currently equipped by the pirate.
 * @property treasure The amount of treasure the pirate has collected.
 * @property visitedTowns A set of towns the pirate has visited.
 * @property weapons A set of weapons the pirate has acquired.
 */
class Pirate(var name: String) {

    var health = 100
    var currentWeapon: Weapon = Weapon.CUTLASS
    var treasure = 0
    val visitedTowns = mutableSetOf<String>()
    val weapons = mutableSetOf(Weapon.CUTLASS)

    /**
     * Checks if the pirate is alive.
     * @return True if health is greater than 0, false otherwise.
     */
    fun isAlive() = health > 0

    /**
     * Reduces the pirate's health by the specified amount.
     * If health drops below 0, it is set to 0.
     *
     * @param damageTaken The amount of damage to inflict.
     */
    fun takeDamage(damageTaken: Int) {

        health -= damageTaken
        if (health < 0) {
            health = 0
            println("You've been defeated! Game over.")
        }
    }

    /**
     * Increases the pirate's health by the specified amount.
     * Health cannot exceed 100.
     *
     * @param healing The amount of health to restore.
     */
    fun heal(healing: Int) {

        health += healing
        if (health > 100) {
            health = 100
        }
    }

    /**
     * Adds the specified weapon to the pirate's collection.
     *
     * @param weapon The weapon to acquire.
     */
    fun acquireWeapon(weapon: Weapon) {
        weapons.add(weapon)
        println("You've acquired a ${'$'}{weapon.name.lowercase()}!")
    }

    /**
     * Allows the pirate to change their currently equipped weapon.
     * Displays a list of acquired weapons and prompts the user to choose one.
     * If the choice is invalid or the pirate has only one weapon, the current weapon remains unchanged.
     */
    fun changeWeapon() {

        if (weapons.size <= 1) return

        println("\nChoose your weapon:")
        val weaponList = weapons.toList()
        weaponList.forEachIndexed { index, weapon ->
            println("${index + 1}. ${weapon.name.lowercase()} " +
                    "(Damage: ${weapon.damage}, Accuracy: ${weapon.accuracy * 100}%)")
        }
        print("Enter weapon number: ")
        val choice = readlnOrNull()?.toIntOrNull()

        if (choice != null && choice > 0 && choice <= weaponList.size) {
            currentWeapon = weaponList[choice - 1]
            println("You've switched weapons to ${currentWeapon.name.lowercase()}.")
        }
        else {
            println("Invalid choice. Keeping your ${currentWeapon.name.lowercase()}.")
        }


    }
}
