# HTML Element Finder

Program that analyzes HTML and finds a specific element, even after changes, using a set of extracted attributes.

## How it works

Program reads all attributes from origin element and compares to all elements of the same type from sample file.
If percent of similar elements more than allowed similarity percent then adds element to result list.  
Path and id not affect comparison.
Default allowed similarity percent = 50%

## Build

To build application do the following:

```
git clone https://github.com/TimurD/HTML-Element-Finder.git
cd HTML-Element-Finder/
./gradlew fatJar
```

## Run 

To run this application run do the following:  
```
java -jar <your_bundled_app>.jar <input_origin_file_path> <input_other_sample_file_path> <target_element_id>
```
where:  
<your_bundled_app> - "html-element-finder-all-0.0.1" from "build/libs";  
<input_origin_file_path> - origin sample path to find the element with attribute id=<target_element_id> and collect all the required information;  
<target_element_id> - id of target element;  
<input_other_sample_file_path> - path to diff-case HTML file to search a similar element;

## Output

Program prints count of similar elements, path to every element and percent of similarity.

Path example: parentTag#parentTagId.parentTagClass/childTag#childTagId.childTagClass 