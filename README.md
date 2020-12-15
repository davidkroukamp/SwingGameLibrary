# SwingGameLibrary
A simple game library made using Java Swing for Java Swing.
In contrast to other game libraries in Java this does not use OpenGL and instead uses Swing components only.

**TODO** (before alpha release):
- [x] Remove `Scene` reference from `GameLoop`
- [x] Iterate sprites using iterator or synchronized block in places that access sprite list
- [ ] Add an Audio Manager
- [ ] Allow debug details to be shown when enabled (FPS, objects rendered)
- [ ] ImageScaler to be built into Scene.java (so no point calcs are needed from a user perspective)
- [ ] Add physics capabilities using [dyn4j](https://github.com/dyn4j/dyn4j)
- [ ] Document classes and methods
- [ ] Add spritesheet/plist support for `SpriteFrameCache`

**Examples:**

See [SwingGameLibrary-Samples
](https://github.com/davidkroukamp/swinggamelibrary-samples)
