import kotlin.random.Random

/**
 * Represents a base enemy that the pirate can encounter in the game.
 *
 * @property name The name of the enemy.
 * @property health The current health points of the enemy.
 * @property damage The amount of damage the enemy inflicts with a successful attack.
 * @property accuracy The probability (between 0.0 and 1.0) of the enemy's attack hitting the target.
 */
open class Enemy(
    val name: String,
    var health: Int,
    val damage: Int,
    val accuracy: Double
) {

    /**
     * Checks if the enemy is still alive.
     * @return `true` if health is greater than 0, `false` otherwise.
     */
    fun isAlive() = health > 0

    /**
     * Reduces the enemy's health by the specified amount of damage.
     * Health will not drop below 0.
     *
     * @param damageTaken The amount of damage to inflict on the enemy.
     */
    fun takeDamage(damageTaken: Int) {
        health -= damageTaken
        if (health < 0) health = 0
    }

    /**
     * Performs an attack on the pirate.
     * The success of the attack is determined by the enemy's accuracy.
     * If successful, it inflicts damage on the pirate.
     *
     * @param pirate The pirate to attack.
     */
    open fun attack(pirate: Pirate) {
        println("\n${name}'s turn!")
        if (Random.nextDouble() < accuracy) {
            val damageDealt = damage
            pirate.takeDamage(damageDealt)
            println(
                "$name dealt $damageDealt damage to the pirate!" +
                    "\nThe pirate's health is now ${pirate.health}."
            )
        } else {
            println("$name's attack missed!")
        }
    }
}
