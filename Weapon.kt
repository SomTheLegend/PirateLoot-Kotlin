/**
 * Represents the different kinds of weapons available in the pirate game.
 * Each weapon has a specific damage output and accuracy.
 *
 * @property damage The amount of damage the weapon inflicts.
 * @property accuracy The probability (between 0.0 and 1.0) of the weapon hitting its target.
 */
enum class Weapon(val damage: Int, val accuracy: Double) {

    /** A short, curved sword, common among pirates. Offers a balance of damage and accuracy. */
    CUTLASS(15, 0.75),

    /** A handheld firearm. Deals more damage than a cutlass but is less accurate. */
    PISTOL(30, 0.65),

    /** A short-barreled large-bore firearm. Inflicts high damage but has low accuracy. */
    BLUNDERBUSS(50, 0.45),

    /** A large, heavy piece of artillery. Deals massive damage but is very inaccurate. */
    CANNON(100, 0.25)
}
