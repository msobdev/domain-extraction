# domain-extraction
Extract links from URL

# About
For given URL address provides the list of unique domains which are linked from given URL
and number of links per domain. Takes into account only links of a tags. 

# Html source
Parsing HTML document is achieved with Jsoup library which allow us to get elements and attributes
from document.
https://jsoup.org/

# Extract domain
Extracting domain from the link is done with regular expressions. 

Ignores the main domain links. For instance:
product.oracle.com/index.html or blog.oracle.com/en/.

# Example:
Input:

  https://www.oracle.com/
  
Output:
  - google.com - 1
  - mozaicreader.com - 3
  - java.com - 1
  - youtube.com - 1
