import AssemblyKeys._ // put this at the top of the file

assemblySettings

jarName in assembly := "phoenix_demo32.jar"

mainClass in assembly := Some( "com.sg.pnx.Game" )
