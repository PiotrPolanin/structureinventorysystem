import axios from "axios";

export const http = axios.create({
  baseUrl: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});
