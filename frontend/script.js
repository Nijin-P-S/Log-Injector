//The function to fetch data i.e Log Entry to send to Backend API to store
function submitInsertLogForm() {
    const logDataTextarea = document.getElementById('logData');
    const logData = logDataTextarea.value.trim();

    if (logData !== '') {
        try {
            // Parse the log data to JSON
            const jsonData = JSON.parse(logData);

            // Log the JSON object to the console -- This can be avoided, done for understanding flow
            console.log('Parsed JSON:', jsonData);

            sendToBackend(jsonData);
        } catch (error) {
            console.error('Error parsing JSON:', error);
            alert('Error parsing JSON. Please provide valid JSON data.');
        }
    } else {
        alert('Please enter JSON log data.');
    }
}

function sendToBackend(jsonData) {
    fetch('http://localhost:3000/api/v1/logIngestor/insertLogs/', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Custom-Header': 'CustomValue',
            'Origin': 'http://your-frontend-origin.com',
        },
        body: JSON.stringify(jsonData),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Server response:', data);
        // Display the server response in the response container
        displayResponse(data);
    })
    .catch(error => {
        console.error('Error sending data to the server:', error);
    });
}

function submitSearchLogsForm() {
    const formData = {};

    // Get values from selected and filled text areas for search logs form
    document.querySelectorAll('.modern-checkbox:checked').forEach(checkbox => {
        const fieldName = checkbox.id.replace('Checkbox', '');
        const textarea = document.getElementById(fieldName);

        //Added specific condition as this is a nested value
        if (fieldName === 'parentResourceId') {
            if (textarea.value !== '' && textarea.value !== null) {
                // Include the field inside 'metadata'
                formData.metadata = {
                    parentResourceId: textarea.value
                };
            }
            } else {
                // For other fields, check if the value is not empty or null
                if (textarea.value !== '' && textarea.value !== null) {
                    // Include the field in formData
                    formData[fieldName] = textarea.value;
                }
            }
        }
    );

    console.log(formData);

    // Can be uncommented if you want to see the request in the response container
    displayResponse(formData);

    fetch('http://localhost:3000/api/v1/logIngestor/search', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Custom-Header': 'CustomValue',
            'Origin': 'http://your-frontend-origin.com',
        },
        body: JSON.stringify(formData),
    })
    .then(response => response.json())
    .then(data => {
        displayResponse(data);
    })
    .catch(error => {
        console.error('Error sending data to the server:', error);
    });
}



function submitFullTextForm() {
    const fullTextTextarea = document.getElementById('fullText');
    const fullText = fullTextTextarea.value.trim();

    if (fullText !== '') {
        const jsonData = { fullText };

        fetch(`http://localhost:3000/api/v1/logIngestor/searchWithFullText/`, {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Custom-Header': 'CustomValue',
                'Origin': 'http://your-frontend-origin.com',
            },
            body: JSON.stringify(jsonData),
        })
        .then(response => response.json())
        .then(data => {
            displayResponse(data);
        })
        .catch(error => {
            console.error('Error sending data to the server:', error);
        });
    } else {
        alert('Please enter text.');
    }
}

// Modify the event listener to handle the visibility of the third form
document.querySelectorAll('input[name="logOption"]').forEach(option => {
    option.addEventListener('change', () => {
        const insertLogForm = document.getElementById('insertLogForm');
        const searchLogsForm = document.getElementById('searchLogsForm');
        const fullTextForm = document.getElementById('fullTextForm');

        if (option.value === 'insert') {
            insertLogForm.style.display = 'block';
            searchLogsForm.style.display = 'none';
            fullTextForm.style.display = 'none'; // Hide the fullTextForm
        } else if (option.value === 'search') {
            insertLogForm.style.display = 'none';
            searchLogsForm.style.display = 'block';
            fullTextForm.style.display = 'none'; // Hide the fullTextForm
        } else if (option.value === 'fullText') {
            insertLogForm.style.display = 'none';
            searchLogsForm.style.display = 'none';
            fullTextForm.style.display = 'block'; // Show the fullTextForm
        }
    });
});

function displayResponse(data) {
    const responseElement = document.getElementById('response');
    responseElement.textContent = JSON.stringify(data, null, 2);
}
