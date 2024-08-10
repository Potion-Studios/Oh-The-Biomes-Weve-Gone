# 1.0.7-Beta
- Fix some leaves having incorrect drops and not dropping leaves when sheared
- Make Tall Prairie Grass only placeable on BlockTag Dirt to be the same as normal prairie grass
- Make All VineBlocks Compostable and Climbable
- Move All leaves into Wood Class/Creative Tab
- Fix Holly Berry Leaves Drops
- Fix Grass Generating in caves in Allium Shrubland
- Adjust Just Like Grandma's Lang to say both pies
- Make Stairs and Slabs Tags for BWG SandStone Sets and add to Respected Fabric Tags
- Make BWG Desert Plants, Leaf Piles, Flower and Clover patches Compostable

# 1.0.6-Beta
- Fix Skyris Hanging Sign Textures being swapped
- Fix Blue Enchanted Sapling Crashing on place
- Changes to Fruit Leaves
  - Pass Supplier of BWGFruitBlock to BWGFruitLeavesBlock instead of supplier of blockstate
  - Prevent Bonemealing when leaves are placed by players/decayable/Persistence Value
    - Reason for this is fruit should only really be growing when the leaves are attached to a tree
- Fix Fruit Leaves Crash
- Fix Yucca Trees not being growable on sand tagged blocks
- Add Cracked Sand and Sandy Dirt to Sand Block and item tags
- Make Cypress Saplings use 2 by 2
- Fix Yucca Leaves Models
- Add Light to Fairy Slipper
- Make all BWG flowers have No Occlusion 

# 1.0.5-Beta
- Workaround for a crash involving fruit leaves decay (No Drops)
- Change in how decaying leaves drop there fruit
- Change PrairieGrassMixin.java to use Inject to be more compatible with other mods

# 1.0.4-Beta
- Use Chunk Seeded Random for Crag Gardens Extensions
- Fix Palo Verde Logs and Wood not having drops
- Fix Orchard Trees requiring 2x2
- Fix Incompatibility with Nature's Spirit

# 1.0.3-Beta
- Fix Lush Farmland and grass not having loottables
- Fix sandy farmland not having a loottable
- Remove Vanilla Igloos from Shattered Glacier
- Fix a bunch of Leaves drops
- Remove Extra ModPlatform Classes, Add getConfigPath to PlatformHandler
- Custom Mushroom Sizes
- Add Huge Mushroom growers to mushrooms blocks
- Add Hanging Sign Recipes
- Make advancements not all Challenges

# 1.0.2-Beta
- Fix Golden Apple from Green Apple Recipe overriding Vanilla Recipe
- Fix Jacaranda Bushes Missing Collision
- Move Platform based things to AutoService
- Fix Wood Leaves being in Axe Mineable instead of Hoe Mineable
- Fix Fruitblocks not dropping when the leaf above them decays
- Fix palm saplings requiring 2x2 and dirt and not sand
- Fix Firecracker Leaves dropping fir saplings
- Remove the need for PrairieGrassMixin.java on Forge Side by using BoneMealEvent

# 1.0.1-Beta
- Allow BoneMealing Allium Flower Bushes into Tall Alliums
- Fix Crashing when going near fruit blocks on Fabric
- Fix Podzol Floors in Cakes under Sakura Grove
- Fix incompatibility with William Wythers' Overhauled Overworld
- Add BWG Logs to Logs That Burn Tag
- Fix a bunch of incorrect rendertypes around Plant like blocks
- Remove Surface Lava Pools from Forest like biomes
- Fix ores not genning in Eroded Borealis
- BoneMealing Tall Alliums Makes Giant Alliums
- Increase rate of Pumpkin in Pumpkin Valley

# 1.0.0-Beta
- Initial release