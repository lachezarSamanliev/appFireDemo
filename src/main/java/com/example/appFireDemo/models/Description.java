package com.example.appFireDemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Description {
    private List<Content> content;

//"description":{
//         "version":1,
//         "type":"doc",
//         "content":[
//            {
//               "type":"paragraph",
//               "content":[
//                  {
//                     "type":"text",
//                     "text":"Bug Description "
//                  }
//               ]
//            }
//         ]
//      },
}


