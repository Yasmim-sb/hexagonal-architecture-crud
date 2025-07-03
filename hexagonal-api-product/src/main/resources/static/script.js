const API_BASE_URL = "http://localhost:8080/products/v1";

const screens = {
  welcome: document.getElementById('welcome-screen'),
  createProduct: document.getElementById('create-product'),
  listProducts: document.getElementById('list-products'),
};

const buttons = {
  manageStock: document.getElementById('manage-stock-btn'),
  create: document.getElementById('btn-create'),
  list: document.getElementById('btn-list'),
  cancelCreate: document.getElementById('cancel-create'),
  backToMenu: document.getElementById('back-to-menu'),
  update: document.getElementById('btn-update'),
  delete: document.getElementById('btn-delete'),
  cancelSelection: document.getElementById('btn-cancel-selection'),
};

const productListDiv = document.getElementById('product-list');
const productForm = document.getElementById('product-form');

// NOVO: container dos botões criar/listar dentro do welcome-screen
const stockOptions = document.getElementById('stock-options');

let selectedProduct = null;

// Função para trocar telas
function showScreen(screen) {
  Object.values(screens).forEach(s => s.classList.remove('active-screen'));
  screen.classList.add('active-screen');
}

// Eventos para navegação

// Clicar em iniciar gerenciamento: mostra ou esconde os botões criar/listar
buttons.manageStock.onclick = () => {
  const isVisible = stockOptions.style.display === 'flex';
  stockOptions.style.display = isVisible ? 'none' : 'flex';
};

// Clicar em criar produto abre tela de criação
buttons.create.onclick = () => {
  resetForm();
  showScreen(screens.createProduct);
};

// Clicar em listar produtos abre tela da lista
buttons.list.onclick = () => {
  loadProducts();
  showScreen(screens.listProducts);
};

buttons.cancelCreate.onclick = () => {
  resetForm();
  showScreen(screens.welcome);
  stockOptions.style.display = 'none';
  buttons.manageStock.disabled = false;
  buttons.manageStock.style.opacity = '1';
};

//// Voltar do list-products para welcome, esconde os botões e habilita Iniciar
buttons.backToMenu.onclick = () => {
  selectedProduct = null;
  updateSelectedProductActions();
  showScreen(screens.welcome);
  stockOptions.style.display = 'none';
  buttons.manageStock.disabled = false;
  buttons.manageStock.style.opacity = '1';
};

// Cancelar seleção no list-products
buttons.cancelSelection.onclick = () => {
  clearProductSelection();
  updateSelectedProductActions();
};

// Atualizar produto
buttons.update.onclick = () => {
  if (!selectedProduct) return alert("Selecione um produto primeiro.");
  fillFormForEdit(selectedProduct);
  showScreen(screens.createProduct);
  stockOptions.style.display = 'none';
  buttons.manageStock.disabled = false;
  buttons.manageStock.style.opacity = '1';
};

// Excluir produto
buttons.delete.onclick = async () => {
  if (!selectedProduct) return alert("Selecione um produto primeiro.");
  if (!confirm(`Confirma a exclusão do produto "${selectedProduct.name}"?`)) return;

  const response = await fetch(`${API_BASE_URL}/${selectedProduct.id}`, { method: 'DELETE' });
  if (!response.ok) {
    alert('Erro ao excluir o produto.');
    return;
  }

  selectedProduct = null;
  updateSelectedProductActions();
  loadProducts();
};

// Submit do formulário cria ou atualiza
productForm.onsubmit = async (e) => {
  e.preventDefault();
  await saveProduct();
};

// Carrega produtos e exibe na lista
async function loadProducts() {
  const response = await fetch(API_BASE_URL);
  if (!response.ok) {
    alert('Erro ao carregar produtos');
    return;
  }
  const products = await response.json();

  productListDiv.innerHTML = '';

  products.forEach(product => {
    const div = document.createElement('div');
    div.className = 'product';
    div.innerHTML = `
      <strong>${product.name}</strong> — R$ ${product.price.toFixed(2)}
      <br>
      <span class="product-description">${product.description}</span>
    `;
    div.onclick = () => selectProduct(div, product);
    productListDiv.appendChild(div);
  });

  clearProductSelection();
  updateSelectedProductActions();
}

// Seleciona um produto da lista
function selectProduct(div, product) {
  clearProductSelection();
  div.classList.add('selected');
  selectedProduct = product;
  updateSelectedProductActions();
}

// Limpa seleção visual e variável
function clearProductSelection() {
  [...productListDiv.children].forEach(child => child.classList.remove('selected'));
  selectedProduct = null;
}

// Mostra ou esconde os botões atualizar/excluir conforme seleção
function updateSelectedProductActions() {
  const actionsDiv = document.getElementById('selected-product-actions');
  if (selectedProduct) {
    actionsDiv.style.display = 'flex';
  } else {
    actionsDiv.style.display = 'none';
  }
}

// Preenche formulário para edição
function fillFormForEdit(product) {
  document.getElementById('product-id').value = product.id;
  document.getElementById('name').value = product.name;
  document.getElementById('description').value = product.description;
  document.getElementById('price').value = product.price;
  document.getElementById('save-button').textContent = 'Atualizar Produto';
  document.getElementById('form-title').textContent = 'Atualizar Produto';
}

// Reseta o formulário para criação
function resetForm() {
  document.getElementById('product-id').value = '';
  document.getElementById('name').value = '';
  document.getElementById('description').value = '';
  document.getElementById('price').value = '';
  document.getElementById('save-button').textContent = 'Adicionar Produto';
  document.getElementById('form-title').textContent = 'Criar Produto';
}

// Salva um produto (cria ou atualiza)
async function saveProduct() {
  const idField = document.getElementById('product-id');
  const id = idField.value || generateId();
  const name = document.getElementById('name').value.trim();
  const description = document.getElementById('description').value.trim();
  const price = parseFloat(document.getElementById('price').value);
  const now = new Date().toISOString();

  if (!name || !description || isNaN(price)) {
    alert("Preencha todos os campos corretamente.");
    return;
  }

  const product = {
    id,
    name,
    description,
    price,
    updatedAt: now,
    createdAt: idField.value ? undefined : now,
  };

  try {
    let response;

    if (idField.value) {
      response = await fetch(`${API_BASE_URL}/${id}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(product),
      });
    } else {
      response = await fetch(API_BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(product),
      });
    }

    if (!response.ok) {
      const errorText = await response.text();
      alert(`Erro ao salvar produto: ${errorText}`);
      return;
    }

    resetForm();
    loadProducts();
    showScreen(screens.listProducts);
  } catch (error) {
    alert("Erro ao salvar produto: " + error.message);
  }
}

// Gera ID simples
function generateId() {
  return Date.now().toString();
}

// Inicializa mostrando a tela de boas-vindas
showScreen(screens.welcome);
updateSelectedProductActions();
stockOptions.style.display = 'none';  // garante que os botões estão escondidos