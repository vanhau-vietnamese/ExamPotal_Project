import { useState } from 'react';

function AddButton() {
  const [showForm, setShowForm] = useState(false);
  const [buttonName, setButtonName] = useState('');
  const [buttons, setButtons] = useState([]);
  const [showDeleteDropdown, setShowDeleteDropdown] = useState(false);
  const [selectedButtonIndex, setSelectedButtonIndex] = useState(null);

  const handleAddButtonClick = () => {
    setShowForm(true);
  };

  const handleCreateButton = () => {
    if (buttonName) {
      setButtons([...buttons, buttonName]);
      setButtonName('');
    }
    setShowForm(false);
  };

  const handleDeleteButtonClick = (index) => {
    setSelectedButtonIndex(index);
    setShowDeleteDropdown(true);
  };

  const handleDeleteConfirm = () => {
    if (selectedButtonIndex !== null) {
      const updatedButtons = [...buttons];
      updatedButtons.splice(selectedButtonIndex, 1); // xoa tai vi tri dang chon va update lai mang nut
      setButtons(updatedButtons);
    }
    setSelectedButtonIndex(null);
    setShowDeleteDropdown(false);
  };

  const handleDeleteCancel = () => {
    setSelectedButtonIndex(null);
    setShowDeleteDropdown(false);
  };

  return (
    <div className="flex flex-wrap">
      <button
        className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded m-4"
        onClick={handleAddButtonClick}
      >
        Thêm ngôn ngữ
      </button>
      {showForm && (
        <div className="flex items-center mt-4">
          <input
            type="text"
            placeholder="Nhập tên ngôn ngữ..."
            value={buttonName}
            onChange={(e) => setButtonName(e.target.value)}
            className="border border-gray-400 px-4 py-2 rounded"
          />
          <button
            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded ml-2"
            onClick={handleCreateButton}
          >
            Tạo
          </button>
        </div>
      )}
      <div className="mt-4">
        {buttons.map((button, index) => (
          <div key={index} className="relative inline-block">
            <button
              className="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded mb-4 mr-4"
              onClick={() => handleDeleteButtonClick(index)}
            >
              {button}
            </button>
            {showDeleteDropdown && selectedButtonIndex === index && (
              <div className="absolute top-0 right-0 mt-2 mr-2">
                <div className="bg-white border border-gray-400 rounded shadow-md">
                  <div className="flex justify-end px-4 py-2">
                    <button
                      className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded mr-2"
                      onClick={handleDeleteConfirm}
                    >
                      Xóa
                    </button>
                    <button
                      className="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded"
                      onClick={handleDeleteCancel}
                    >
                      Hủy
                    </button>
                  </div>
                </div>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default AddButton;
