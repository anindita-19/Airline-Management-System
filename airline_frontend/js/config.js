const CONFIG = {
    API_BASE_URL: 'https://airline-backend-i4wj.onrender.com'
};

function getApiUrl(endpoint) {
    return CONFIG.API_BASE_URL + endpoint;
}