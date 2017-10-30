# JSON-parser

### Stack used
IDEA, gradle, GIT, Junit

### Running the app
Program requires 3 command line arguments: config file name, entities file name to be run, result file name.
Ideally full path to ../src/main has to be specified as working directory if run from idea, this also relates to unit tests!  
#### Example
resources\validation_config resources\test-files\test.in resources\result.txt

### Overall structure
Program loads records to validate from fle specified in 2nd argument, example of supported data format: resources\test-files\test.in , 
creates new Validator passing validation config file specified in 1st argument and initiates it,
runs validation on each record and saves the result in JSON with filename specified as 3d argument.      

### Some notes
This can be done much better with some spring magic (dependency injection, configuration loading, etc .).
However the concrete task is to small to over complicate it with heavy frameworks.
        
