package com.secure.assignment1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
@Service
public class ConvertService {

    public String convertJsonFileToXml(MultipartFile file) throws IOException {
        // Read JSON as tree
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(file.getInputStream());

        // Start building XML with root element
        StringBuilder xml = new StringBuilder();
        xml.append("<root>\n");

        // Process root node
        xml.append(processNode(root));

        xml.append("</root>");
        FileWriter writer = new FileWriter(new File("C:\\Users\\DELL\\Desktop\\output.xml"));
        writer.write(xml.toString());
        writer.flush();
        writer.close();
        return xml.toString();
    }

    // Recursive function to process any JSON node
    private String processNode(JsonNode node) {
        StringBuilder xml = new StringBuilder();

        if (node.isObject()) {
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String key = fieldNames.next();
                JsonNode valueNode = node.get(key);
                JsonNodeType type = valueNode.getNodeType();

                switch (type) {
                    case STRING:
                        xml.append("<string name=\"").append(key).append("\">")
                                .append(valueNode.asText())
                                .append("</string>\n");
                        break;
                    case NUMBER:
                        xml.append("<int name=\"").append(key).append("\">")
                                .append(valueNode.asInt())
                                .append("</int>\n");
                        break;
                    case BOOLEAN:
                        xml.append("<boolean name=\"").append(key).append("\">")
                                .append(valueNode.asBoolean())
                                .append("</boolean>\n");
                        break;
                    case NULL:
                        xml.append("<null name=\"").append(key).append("\"/>\n");
                        break;
                    case OBJECT:
                        xml.append("<object name=\"").append(key).append("\">\n")
                                .append(processNode(valueNode))
                                .append("</object>\n");
                        break;
                    case ARRAY:
                        xml.append("<array name=\"").append(key).append("\">\n")
                                .append(processArray(valueNode))
                                .append("</array>\n");
                        break;
                }
            }
        }

        return xml.toString();
    }

    // Process arrays
    private String processArray(JsonNode arrayNode) {
        StringBuilder xml = new StringBuilder();
        for (JsonNode element : arrayNode) {
            JsonNodeType type = element.getNodeType();
            switch (type) {
                case STRING:
                    xml.append("<string>").append(element.asText()).append("</string>\n");
                    break;
                case NUMBER:
                    xml.append("<int>").append(element.asInt()).append("</int>\n");
                    break;
                case BOOLEAN:
                    xml.append("<boolean>").append(element.asBoolean()).append("</boolean>\n");
                    break;
                case NULL:
                    xml.append("<null/>\n");
                    break;
                case OBJECT:
                    xml.append("<object>\n").append(processNode(element)).append("</object>\n");
                    break;
                case ARRAY:
                    xml.append("<array>\n").append(processArray(element)).append("</array>\n");
                    break;
            }
        }
        return xml.toString();
    }
}