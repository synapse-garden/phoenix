phoenix
=======

phoenix is currently under heavy development.  We'll update this space with a timeline when we announce our alpha release.

### Table of Contents

- [Getting phoenix](#getting-phoenix)
- [Using phoenix](#using-phoenix)
- [Build and run](#build-and-run-phoenix)
 - [Getting sbt](#getting-sbt)
 - [Using sbt](#using-sbt-to-run-phoenix)
- [Hacking phoenix](#hacking-phoenix)

# Getting phoenix

```bash
git clone https://github.com/synapse-garden/phoenix.git
```

# Using phoenix

At present, phoenix is not a full-fledged engine.  A demo project is included, which is suitable for testing purposes.
 > See [_hacking phoenix_](#hacking-phoenix) for more details, or read on!

# Build and run phoenix

To build and run phoenix, you'll need [sbt](http://www.scala-sbt.org/), a build tool for [Scala](http://www.scala-lang.org/).

sbt automatically downloads the native dependencies for phoenix, and accelerates the build process.

You'll also need [JDK version 7 or greater](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

## Getting sbt

#### Linux (.deb)
```bash
mkdir ~/tmp-sbt && pushd ~/tmp-sbt
wget http://dl.bintray.com/sbt/debian/sbt-0.13.5.deb
su -c "dpkg -i sbt-0.13.5.deb"
popd
```

#### Linux (.rpm)
```bash
mkdir ~/tmp-sbt && pushd ~/tmp-sbt
wget http://dl.bintray.com/sbt/rpm/sbt-0.13.5.rpm
su -c "yum install sbt-0.13.5.rpm"
popd
```

#### Windows

[Install sbt using the installer](http://dl.bintray.com/sbt/native-packages/sbt/0.13.5/sbt-0.13.5.msi).

#### Mac OS
phoenix uses OpenGL 4.4, so Mac OS [isn't supported yet](https://developer.apple.com/graphicsimaging/opengl/capabilities/).  If you'd like to port to an earlier version of OpenGL, we're always open to [well-specified pull requests](https://github.com/erlang/otp/wiki/Writing-good-commit-messages)!

## Using sbt

Open a terminal and cd to the location where you cloned phoenix.  Then, enter the sbt shell.

```bash
$ sbt

```

You'll see sbt downloading and preparing Scala and other phoenix deps.

When it's ready, you can simply enter `run` to automatically compile and run the project.  You can also `compile` or `test`.

Use `help` to see a list of other options.  sbt is a powerful tool, but its use is outside the scope of this README.

Press `ctrl + d` to exit the sbt shell.

# Hacking phoenix

At SynapseGarden, we use [IntelliJ IDEA](http://www.jetbrains.com/idea/).  Once you've cloned phoenix, it should be trivial to open as an IDEA project.  We also use [`idea-sbt-plugin`](http://plugins.jetbrains.com/plugin/?idea&id=5007).

However, you can use any editor you like, such as [atom.io](https://github.com/atom/atom/blob/master/README.md#building) or [Sublime Text](http://sublimetext.com).

For IDEA, set up a run configuration using the [how-to at IntelliJ's site](http://www.jetbrains.com/idea/webhelp/creating-and-editing-run-debug-configurations.html).  Use the following config:
 - Type: `Application`
 - Main class: `com.sg.pnx.Game`
 - Use classpath of module: `<your project name>`

If you're using idea-sbt-plugin, you'll also want to substitute the `Make` step under "Before launch" with `SBT:compile`.

_See our [Wiki](https://github.com/synapse-garden/phoenix/wiki) for details on the project structure and classes._
