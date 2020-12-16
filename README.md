# SwingGameLibrary
A simple 2D game library made using Java Swing for Java Swing.
In contrast to other 2D game libraries in Java this does not use OpenGL and instead uses Swing components/methodologies only.

The projects nomenclature might seem familiar if you have ever used [cocos2dx](https://github.com/cocos2d/cocos2d-x) and thats because it is what inspired me to write this.

**TODO** (before alpha release):
- [x] Remove `Scene` reference from `GameLoop`
- [x] Iterate sprites using iterator or synchronized block in places that access sprite list
- [x] `Scene` to render black background and not white
- [x] Add a `INode` interface which `Scene` will accept and `Sprite` will implement
- [x] Add a `ICollidable` interface which will allow automatic collision detections
- [x] Allow debug details/masks to be shown/drawn i.e FPS, objects rendered and red rectangles around all nodes when enabled
- [ ] Implement `AudioEngine`
- [ ] Implement `Animator` loops 
- [ ] `ImageScaler` to be built into Scene.java (so no point calcs are needed from a user perspective)
- [ ] Add spritesheet/plist support for `SpriteFrameCache`
- [ ] Add physics capabilities using [dyn4j](https://github.com/dyn4j/dyn4j)
- [ ] Document classes and methods

**Examples:**

See [SwingGameLibrary-Samples
](https://github.com/davidkroukamp/swinggamelibrary-samples)
