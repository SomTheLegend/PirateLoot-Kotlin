import kotlin.random.Random

/**
 * Represents a Mercenary, a more dangerous type of enemy.
 * Mercenaries have a chance to inflict critical hits for double damage.
 * Inherits from the [Enemy] class.
 *
 * @param name The name of the mercenary.
 * @param health The current health points of the mercenary.
 * @param damage The base amount of damage the mercenary inflicts with a successful attack.
 * @param accuracy The probability (between 0.0 and 1.0) of the mercenary's attack hitting the target.
 */
class Mercenary(
    name: String,
    health: Int,
    damage: Int,
    accuracy: Double
) : Enemy(name, health, damage, accuracy) {

    /**
     * Performs an attack on the pirate.
     * The success of the attack is determined by the mercenary's accuracy.
     * There is a 20% chance of a critical hit, which deals double damage.
     * If successful, it inflicts damage on the pirate.
     *
     * @param pirate The pirate to attack.
     */
    override fun attack(pirate: Pirate) {
        println("\n${name}'s turn!")
        if (Random.nextDouble() < accuracy) {
            var damageDealt = damage // Use var to allow modification for critical hit
            if (Random.nextDouble() < 0.20) { // 20% chance for a critical hit
                damageDealt *= 2
                println("The $name lands a critical hit!")
            }
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
