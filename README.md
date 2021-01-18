# SwingGameLibrary
A simple 2D game library made using Java Swing for Java Swing.
In contrast to other 2D game libraries in Java this does not use OpenGL and instead uses Swing components/methodologies only.
The allows you to be able to leverage existing knowledge of Java Swing components, layouts, UIs and design patterns in order to create a functional 2D game.

The projects nomenclature might seem familiar if you have ever used [cocos2dx](https://github.com/cocos2d/cocos2d-x) and thats because it is what inspired me to write this.

***

**TODO** (before beta release):
- [x] Remove `Scene` reference from `GameLoop`
- [x] Iterate sprites using iterator or synchronized block in places that access sprite list
- [x] `Scene` to render black background and not white
- [x] Add a `INode` interface which `Scene` will accept and `Sprite` will implement
- [x] Add a `ICollidable` interface which will allow automatic collision detections
- [x] Allow debug details/masks to be shown/drawn i.e FPS, objects rendered and red rectangles around all nodes when enabled
- [x] Implement `AudioEngine`
- [x] `INode` should be able to be added to another `INode` and rendered
- [x] `Scene` should be nothing more then an `INode` with a `Director` which extends `JPanel` running the game loop and switching between `Scene`s
- [x] `Node` co-ordinates should be relative to parent. `Scene` node should default by obtaining width and height from the `Director`.
- [x] Implement `Animation` loops as currently all animations run indefinitely 
- [x] `ImageScaler` to be first class citizen built into `Director`/`Scene` (so no point calcs for various screen widths are needed from a user perspective)
- [x] `parent` should be nulled when `Node#remove` is called.
- [x] Checking for collisions should use a copy of the nodes as to avoid concurrent modification issues (when adding inside the `onCollision` method)
- [x] Add z-order to `Node` as well as fix Node size counts
- [x] `Node` should have `onEnter`/`onExit` callbacks for when a `Node` is made visible or removed from the scene/parent.
- [x] Remove any unnecessary synchronized blocks 
- [x] Rename all getters with *world* in to omit the world part for readability
- [x] Add Spritesheet support for `SpriteFrameCache`
- [x] Allow `Sprite` to be created with a single `SpriteFrame` as opposed to animation
- [ ] Implement `setFlippedY` on `Sprite`
- [ ] Allow `Node` background to be set with a `SpriteFrame` similar to `Sprite`
- [ ] Add `MouseListener` ability to `Node`s using `Shape`/`Ellipses2D`
- [ ] Allow `Director` to use *variable* or *fixed* step game loop. In *fixed* step `elapsedTime` will simply be <=0
- [ ] Implement some type of `ActionManager`/`Actionable` class on `Node` which `Sprite` will use to switch out animations on the fly. Animate and MoveTo.
- [ ] Allow more support of *plist* and TexturePacker formats like cropping and trimming of sprites, and rotated sprites as well as multi packed sheets
- [ ] Add *Tileset*/*Tilemap* support, which is used in its own sample (sample-2)
- [ ] Add `Camera` which can be used for side scrolling/infinite background game types
- [ ] `Scene` should only render visible sprites in the camera view
- [ ] Implement assets search paths (SD,HD,HDR)
- [ ] Add physics capabilities using [dyn4j](https://github.com/dyn4j/dyn4j)
- [ ] Document classes and methods

***

**Requirements:**

- [Java JDK 8](https://www.oracle.com/za/java/technologies/javase/javase-jdk8-downloads.html)
- [NetBeans IDE 8.2+](https://netbeans.org/downloads/8.2/rc/)

**Examples:**

See [SwingGameLibrary-Samples
](https://github.com/davidkroukamp/swinggamelibrary-samples)


**Special thanks to:**
- [TinySound](https://github.com/finnkuusisto/TinySound) for the great library, which is the backbone of *SwingGameLibrary's* `AudioEngine`
- [jcabi-xml](https://github.com/jcabi/jcabi-xml) for the XML library used to read *plist* file format
