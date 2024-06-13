let isEditing = false;
const API_ENDPOINT = '/themes';
const cellFields = ['id', 'name', 'description', 'thumbnail'];
const createCellFields = ['', createInput(), createInput(), createInput()];
function createBody(inputs) {
  return {
    name: inputs[0].value,
    description: inputs[1].value,
    thumbnail: inputs[2].value,
  };
}

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('add-button').addEventListener('click', addRow);
  requestRead()
      .then(render)
      .catch(error => console.error('Error fetching times:', error));
});

function render(data) {
  const tableBody = document.getElementById('table-body');
  tableBody.innerHTML = '';

  data.forEach(item => {
    const row = tableBody.insertRow();

    cellFields.forEach((field, index) => {
      row.insertCell(index).textContent = item[field];
    });

    const actionCell = row.insertCell(row.cells.length);
    actionCell.appendChild(createActionButton('삭제', 'btn-danger', deleteRow));
  });
}

function addRow() {
  if (isEditing) return;  // 이미 편집 중인 경우 추가하지 않음

  const tableBody = document.getElementById('table-body');
  const row = tableBody.insertRow();
  isEditing = true;

  createAddField(row);
}

function createAddField(row) {
  createCellFields.forEach((field, index) => {
    const cell = row.insertCell(index);
    if (typeof field === 'string') {
      cell.textContent = field;
    } else {
      cell.appendChild(field);
    }
  });

  const actionCell = row.insertCell(row.cells.length);
  actionCell.appendChild(createActionButton('확인', 'btn-custom', saveRow));
  actionCell.appendChild(createActionButton('취소', 'btn-secondary', () => {
    row.remove();
    isEditing = false;
  }));
}

function createInput() {
  const input = document.createElement('input');
  input.className = 'form-control';
  return input;
}

function createActionButton(label, className, eventListener) {
  const button = document.createElement('button');
  button.textContent = label;
  button.classList.add('btn', className, 'mr-2');
  button.addEventListener('click', eventListener);
  return button;
}

function saveRow(event) {
  const row = event.target.parentNode.parentNode;
  const inputs = row.querySelectorAll('input');
  const body = createBody(inputs);

  requestCreate(body)
      .then(() => {
        location.reload();
      })
      .catch(error => console.error('Error:', error));

  isEditing = false;  // isEditing 값을 false로 설정
}

function deleteRow(event) {
  const row = event.target.closest('tr');
  const id = row.cells[0].textContent;

  requestDelete(id)
      .then(() => row.remove())
      .catch(error => console.error('Error:', error));
}


// request

function requestCreate(data) {
  const requestOptions = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data)
  };

  return fetch(API_ENDPOINT, requestOptions)
      .then(response => {
        if (response.status === 201) return response.json();
        throw new Error('Create failed');
      });
}

function requestRead() {
  return fetch(API_ENDPOINT)
      .then(response => {
        if (response.status === 200) return response.json();
        throw new Error('Read failed');
      });
}

function requestDelete(id) {
  const requestOptions = {
    method: 'DELETE',
  };

  return fetch(`${API_ENDPOINT}/${id}`, requestOptions)
      .then(response => {
        if (response.status !== 204) throw new Error('Delete failed');
      });
}
