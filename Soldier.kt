/**
 * Represents a standard Soldier enemy in the pirate game.
 * Soldiers are a basic type of enemy and inherit their behavior directly from the [Enemy] class.
 * They do not possess any special abilities or attack patterns beyond the default [Enemy.attack] method.
 *
 * @param name The name of the soldier.
 * @param health The current health points of the soldier.
 * @param damage The amount of damage the soldier inflicts with a successful attack.
 * @param accuracy The probability (between 0.0 and 1.0) of the soldier's attack hitting the target.
 */
class Soldier(
    name: String,
    health: Int,
    damage: Int,
    accuracy: Double,
) : Enemy(name, health, damage, accuracy)
