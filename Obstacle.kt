/**
 * Represents a non-enemy hazard that can harm the pirate.
 * These are typically environmental dangers found within the game world, such as in towns.
 *
 * @property name The name of the obstacle (e.g., "Quicksand", "Falling Rocks").
 * @property damage The amount of damage the obstacle inflicts upon the pirate when triggered.
 */
class Obstacle(val name: String, val damage: Int) {

    /**
     * Triggers the obstacle's effect on the pirate.
     * This will cause the pirate to take damage.
     * A warning message is displayed if the pirate's health drops below 50.
     *
     * @param pirate The pirate who encountered the obstacle.
     */
    fun trigger(pirate: Pirate) {
        println("Watch out! You've encountered a $name!")
        pirate.takeDamage(damage) // Assuming Pirate class has a takeDamage method
        println("You took $damage damage! Your health is now ${pirate.health}.")
        if (pirate.health < 50 && pirate.isAlive()) { // Assuming Pirate has isAlive and health
            println(
                "You're badly wounded! You should search for a " +
                    "health potion before fighting."
            )
        }
    }
}
