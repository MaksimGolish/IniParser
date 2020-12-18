package com.example.report.service;

import com.example.report.config.ReportProperties;
import com.example.report.entity.ReportTask;
import com.example.report.exception.ServiceConnectionRefusedException;
import com.example.report.exception.TaskDeserializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ManagerConnectionService {
    private final ReportProperties properties;
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<ReportTask> getAllTasks(UUID id) {
        return deserialize(
                getResponseBody("/sprint/" + id + "/tasks")
        );
    }

    public List<ReportTask> getAllTasksByDate(UUID id, String startDate, String endDate) {
        return deserialize(
                getResponseBody("/sprint/" + id + "/tasks?startDate=" + startDate + "&endDate=" + endDate)
        );
    }

    private String getResponseBody(String mapping) {
        String url = "http://" + properties.getTargetPort() + mapping;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new ServiceConnectionRefusedException(url);
        }
    }

    private List<ReportTask> deserialize(String json) {
        try {
            return mapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException exception) {
            throw new TaskDeserializationException(json, exception);
        }
    }
}
