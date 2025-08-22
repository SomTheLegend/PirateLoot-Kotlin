/**
 * Represents a level in the pirate game.
 * Each level consists of a number and a list of towns that the pirate needs to visit and loot.
 *
 * @property levelNumber The unique identifier for the level.
 * @property towns A list of [Town] objects that are part of this level.
 */
class Level(val levelNumber: Int, val towns: List<Town>) {

    /**
     * Checks if the pirate has visited all towns in the level and looted them sufficiently.
     *
     * @param pirate The [Pirate] whose progress is being checked.
     * @return `true` if all towns in the level have been visited and sufficiently looted by the pirate, `false` otherwise.
     */
    fun areAllTownsVisitedAndLooted(pirate: Pirate): Boolean {
        return towns.all { pirate.visitedTowns.contains(it.name) && it.isLootedSufficiently }
