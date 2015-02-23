Bot Talk
=================================

Developed by Connor Hollasch and Jason Stallkamp.

=================================

Interesting features include,
  - Ability to toggle particle simulations on and off simultaneously.
  - Modify settings to specific emitters.
  - Change particle update frequency (global).
  - Change max amount of particles displayed on the screen.
  - Enable a lightweight debug mode for developers.
  - Create simple particle systems for any JFrame application!
  
  ```java
  final ParticleSystem host = new ParticleSystem(5);
  host.addRespawnTask(new SnowSpawnController().setHost(host).setFrequency(10));
  host.addRespawnTask(new FireworkSpawnController().setHost(host).setFrequency(10));
  host.addRespawnTask(new StarSpawnController().setHost(host).setFrequency(10));
  host.addRespawnTask(new FireflySpawnController().setHost(host).setFrequency(10));
  //etc
  ```

Used as an **AP Computer Science** class demonstration during *STEM Fest* at Mount Si High School 2015.

Some pictures of the application in use included below.

![Fireworks](http://i.gyazo.com/8bd30bd3dd8c4b67a6534fd15e4dd2c3.png)
![Snow](http://i.gyazo.com/6e245304f8422feb5986e8431c154c11.png)
![Zooming through space](http://i.gyazo.com/aa55728642d3b9a1fc42d6ea77809465.png)
![Playing with the settings](http://i.gyazo.com/62408c19463d33fe15f9f54994097303.png)
![Seamless simulations](http://i.gyazo.com/07f2fa2b611e2744cd332545a58c316c.png)
![Debug mode](http://i.gyazo.com/de6921affcac50ed1b71c559cfcfc170.png)
