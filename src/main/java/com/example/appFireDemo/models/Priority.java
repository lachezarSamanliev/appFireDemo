package com.example.appFireDemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Priority {
    private String name;
    private String id;
//priority
    //"priority":{
    //         "self":"https://binarybuff.atlassian.net/rest/api/3/priority/3",
    //         "iconUrl":"https://binarybuff.atlassian.net/images/icons/priorities/medium.svg",
    //         "name":"Medium",
    //         "id":"3"
    //      },
    //
}
