// ignore_for_file: avoid_print

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'camera_example.dart';


Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  final MethodChannel _channel = const MethodChannel('com.mt.isl/sdk');

  @override
  void initState() {
    print('Initializing state...');
    setMethodChannelHandler();
    _incrementCounter();
    super.initState();
  }

  void _incrementCounter() {
    print('Incrementing counter... $_counter');
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'Flutter Page',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            FilledButton(
                onPressed: () {
                  Navigator.push(context, MaterialPageRoute(builder: (context) => const CameraExampleHome()));
                },
                child: const Text('Open Camera')),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }

  void setMethodChannelHandler() {
    print('Setting method channel handler...');
    _channel.setMethodCallHandler((call) async {
      print('Method channel handler called with method: ${call.method}');
      switch (call.method) {
        case 'openCamera':
          Navigator.push(context, MaterialPageRoute(builder: (context) => const CameraExampleHome())).then((_) {
            navigateBack();
          });
          break;
      }
    });
  }

  void navigateBack() {
    if (Navigator.canPop(context) && context.mounted) {
      Navigator.pop(context);
    } else {
      SystemNavigator.pop();
    }
  }
}
