# FrameSequence
This library is used to display animated WebP image file. 
It uses 3 native libraries to implement the function: libwebp, libgif, framesequence.

## How to use

You can use this library in two ways:
### 1 download all the source code of framesequence library, then import it into your own project as a library module
for this approach is very simple, no need to make any introductions

### 2 just use aar dependency
1. download the framesSquencce-debug.aar file in this project.

2. copy this file into your project's libs directory

3. add the following changes in your module's build.gradle file
```
repositories {
    flatDir {
        dirs 'libs'
    }
}
```

4. add dependency in build.gradle
```
compile (name: 'framesSquencce', ext: 'aar')
```

5 in Java Activity, use widget `FrameSequence` and `FrameSequenceDrawable` to show webp image<br>
for details, please take references from `FrameSequenceTest.java` in sample
