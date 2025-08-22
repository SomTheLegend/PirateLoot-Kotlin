/**
 * Main object for the Pirate Game.
 * This object initializes and manages the game's levels, towns, and the pirate's progression.
 * It contains the main game loop and handles player input for navigation and actions.
 */
object PirateGame {

    /**
     * A list of [Level] objects that constitute the game's content.
     * Each level contains a list of towns to be explored and looted.
     */
    private val levels = listOf(
        Level(1, listOf(
            Town("Port Blossom", 100, 50,
                obstacles = listOf(Obstacle("Quicksand", 5)),
                enemies = listOf(Soldier("Town Guard", 40, 10,
                    0.7))),

            Town("Sandy Creek", 120, 60,
                obstacles = listOf(Obstacle("Falling Coconut", 5)),
                enemies = listOf(Soldier("Town Guard", 45, 12,
                    0.7))),

            Town("Whispering Isle", 150, 70,
                obstacles = listOf(Obstacle("Poisonous Spider", 10)),
                enemies = listOf(Soldier("Veteran Town Guard", 50, 15,
                    0.75))),
        )),

        Level(2, listOf(
            Town("Gator's End", 200, 100,
                obstacles = listOf(Obstacle("Snapping Crocodile", 15)),
                enemies = listOf(Mercenary("Swamp Mercenary", 60, 20,
                    0.65))),

            Town("Swamp Foot", 220, 110,
                obstacles = listOf(Obstacle("Leech-infested water", 15)),
                enemies = listOf(Soldier("Swamp Patroller", 55, 15,
                    0.8), Mercenary("Swamp Mercenary", 60, 20,
                        0.65))),

            Town("Misty Mangrove", 250, 120,
                obstacles = listOf(Obstacle("Sudden Sinkhole", 15),
                    Obstacle("Snapping Crocodile", 15)),
                enemies = listOf(Mercenary("Elite Mercenary", 70, 25,
                    0.7))),
    )),

        Level(3, listOf(
            Town("Serpent's Coil", 300, 150,
                obstacles = listOf(Obstacle("Venomous Snake", 20)),
                enemies = listOf(Mercenary("Jungle Assassin", 80, 25,
                    0.75))),

            Town("Viper's Nest", 320, 160,
                obstacles = listOf(Obstacle("Spike Trap", 25)),
                enemies = listOf(Soldier("Temple Guard", 70, 20,
                    0.8), Mercenary("Jungle Assassin", 85, 25,
                    0.75))),

            Town("Poisoned Spring", 350, 170,
                obstacles = listOf(Obstacle("Dart Trap", 20),
                    Obstacle("Venomous Snake", 20)),
                enemies = listOf(Mercenary("Temple Executioner", 90, 30,
                    0.8))),
        )),

        Level(4, listOf(
            Town("Fort Courage", 400, 200,
                obstacles = listOf(Obstacle("Landmine", 30)),
                enemies = listOf(Soldier("Fortress Soldier", 100, 30,
                    0.8))),

            Town("Cannonball Bay", 450, 220,
                obstacles = listOf(Obstacle("Stray Cannonball", 35)),
                enemies = listOf(Soldier("Cannoneer", 110, 35,
                    0.8), Mercenary("Fortress Mercenary", 120,
                    35, 0.8))),

            Town("The Garrison", 500, 250,
                obstacles = listOf(Obstacle("Barbed Wire", 25)),
                enemies = listOf(Soldier("Garrison Captain", 150, 40,
                    0.85), Soldier("Elite Guard", 110,
                    35, 0.8))),
        )),

        Level(5, listOf(
            Town("Jaguar Jungle", 550, 270,
                obstacles = listOf(Obstacle("Shadow Cat Pounce", 30)),
                enemies = listOf(Mercenary("Jungle Stalker", 140, 40,
                    0.8))),

            Town("The Lost Ruins", 600, 300,
                obstacles = listOf(Obstacle("Crumbling Floor", 25),
                    Obstacle("Ancient Curse", 15)),
                enemies = listOf(Soldier("Undead Soldier", 130, 40,
                    0.7), Mercenary("Ruin Guardian", 160, 45,
                    0.8))),

            Town("Temple of Fangs", 650, 320,
                obstacles = listOf(Obstacle("Swinging Blade Trap", 40)),
                enemies = listOf(Mercenary("High Priest", 180, 45,
                    0.85), Mercenary("Temple Fanatic", 150,
                    40, 0.85))),
        )),

        Level(6, listOf(
            Town("Volcano's Heart", 800, 400,
                obstacles = listOf(Obstacle("Lava Geyser", 50)),
                enemies = listOf(Enemy("Lava Elemental", 200, 50,
                    0.75))),

            Town("The Obsidian Fortress", 900, 450,
                obstacles = listOf(Obstacle("Obsidian Shards", 40)),
                enemies =  listOf(Soldier("Obsidian Knight", 220, 55,
                    0.8), Mercenary("Obsidian Guard", 250, 55,
                    0.75))),

            Town("The Mad King's Treasury", 1500, 750,
                obstacles = listOf(Obstacle("Royal Treasury Trap", 60)),
                enemies = listOf(Mercenary("The Mad King", 500, 65,
                    0.9), Soldier("Royal Guard", 300, 60,
                        0.9))),
        ))
        )

    /**
     * Starts the pirate game.
     * Initializes the pirate character, displays a welcome message,
     * and then iterates through each level of the game.
     * The game progresses level by level, with the pirate needing to loot all towns
     * in the current level to advance. The game ends if the pirate is defeated or
     * successfully completes all levels.
     */
    fun start() {

        println("--- Welcome to Pirate's Loot! ---")
        print("Enter your pirate's name: ")
        val pirateName = readlnOrNull()?.ifBlank { "Captain Fearless" } ?: "Captain Fearless"
        val pirate = Pirate(pirateName)
        println("Welcome, Captain $pirateName!")

        for (level in levels) {
            println("\n--- Entering Level ${level.levelNumber} ---")

            while (!level.areAllTownsVisitedAndLooted(pirate) && pirate.isAlive()) {
                println("\nCaptain ${pirate.name}, your stats:")
                println("Health: ${pirate.health}")
                println("Treasure: ${pirate.treasure}")
                println("Current Weapon: ${pirate.currentWeapon.name.lowercase()}")

                level.towns.forEachIndexed { index, town ->
                    val visitedMark = if (pirate.visitedTowns.contains(town.name))
                        if (town.isLootedSufficiently) "[LOOTED]" else "[VISITED]" else ""
                    println("${index + 1}. ${town.name} $visitedMark")
                }
                print("Choose a town to loot (enter number): ")

                val choice = readlnOrNull()?.toIntOrNull()
                if (choice != null && choice > 0 && choice <= level.towns.size) {
                    val town = level.towns[choice - 1]
                    if (!pirate.visitedTowns.contains(town.name) && town.isLootedSufficiently) {
                        println("You've already sufficiently looted ${town.name}!")
                    } else {
                        town.loot(pirate)
                    }
                }
                else {
                    println("Invalid choice. Please enter a valid number.")
                }
            }

            if (!pirate.isAlive()) { // Game over if pirate is defeated mid-level
                println("\n--- GAME OVER ---")
                println("Captain ${pirate.name}, you have been defeated!")
                println("Final Treasure: ${pirate.treasure}")
                return
            }

            // This condition means the pirate is alive but didn't loot all towns.
            // However, the while loop condition !level.areAllTownsVisitedAndLooted(pirate)
            // already handles this. If the loop exits and the pirate is alive,
            // it means all towns WERE looted. So, this specific game over condition might be redundant
            // or there's a logic nuance I'm missing. For now, I'll assume the intention
            // is to check if all towns were cleared before proceeding.
            if (!level.areAllTownsVisitedAndLooted(pirate)) {
                println("\n--- GAME OVER ---")
                println("Captain ${pirate.name}, you have been defeated!") // This message might be confusing if pirate is alive
                println("You failed to loot all towns sufficiently and can't proceed.")
                println("Final Treasure: ${pirate.treasure}")
                return
            }

            println("\nCongratulations! You've conquered Level ${level.levelNumber}!")

            if (level.levelNumber < levels.size) {
                println("You found a treasure map to a hidden weapon cache!")
                val powerfulWeapon = Weapon.entries.filter { !pirate.weapons.contains(it) }
                    .randomOrNull()
                if (powerfulWeapon != null) {
                    pirate.acquireWeapon(powerfulWeapon)
                }
                else {
                    println("...but it seems you already have all the weapons from it.")
            }
                println("You also rest and recover your health.")
                pirate.heal(50)
            }
        }

        // Victory condition: pirate is alive and has gone through all levels
        if (pirate.isAlive()) {
            println("\n--- VICTORY ---")
            println("You have conquered all the levels and become the most feared " +
                    "pirate on the seven seas!")
            println("Final Treasure: ${pirate.treasure}")
        } else {
            // This case should ideally be caught earlier, after a level or combat.
            // If reached, it means the pirate was defeated on the very last action of the last level.
            println("\n--- GAME OVER ---")
            println("Captain ${pirate.name}, you were defeated at the final hurdle!")
            println("Final Treasure: ${pirate.treasure}")
        }
    }
}
