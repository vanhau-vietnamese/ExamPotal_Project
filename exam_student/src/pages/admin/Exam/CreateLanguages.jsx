import { useState } from 'react';
import { axiosClient } from '~/apis';

const CreateLanguages = () => {
  const [selectedLanguage, setSelectedLanguage] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const [newLanguage, setNewLanguage] = useState('');
  const [languages, setLanguages] = useState([]);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  const selectLanguage = (language) => {
    setSelectedLanguage(language);
    setIsOpen(false);
  };

  const handleInputChange = (event) => {
    setNewLanguage(event.target.value);
  };

  const addLanguage = async () => {
    if (newLanguage.trim() !== '') {
      try {
        const response = await axiosClient.post('/category/add', {
          title: newLanguage,
        });
        console.log('Response:', response.data);
        const updatedLanguages = [...languages, newLanguage];
        setLanguages(updatedLanguages);
        setSelectedLanguage(newLanguage);
        setIsOpen(false);
        console.log('Ngôn ngữ vừa được tạo:', newLanguage);
        setNewLanguage('');
      } catch (error) {
        console.error('Error:', error);
      }
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      addLanguage();
    }
  };

  return (
    <div className="relative">
      <button
        onClick={toggleDropdown}
        className="inline-flex items-center px-4 py-2 border border-gray-300 rounded-md shadow-sm bg-white text-sm font-medium text-gray-700 hover:bg-gray-50"
      >
        {selectedLanguage ? selectedLanguage : 'Chọn ngôn ngữ'}
      </button>
      {isOpen && (
        <div className="absolute left-0 mt-2 max-w-[150px] origin-top-left bg-white divide-y divide-gray-100 rounded-md shadow-lg ring-1 ring-black ring-opacity-5 z-10">
          {languages.map((language, index) => (
            <button
              key={index}
              onClick={() => selectLanguage(language)}
              className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900"
              role="menuitem"
            >
              {language}
            </button>
          ))}
          <div className="px-4 py-2 text-sm">
            <input
              type="text"
              placeholder="Thêm ngôn ngữ mới..."
              className="border border-gray-300 rounded-md shadow-sm px-2 py-1"
              value={newLanguage}
              onChange={handleInputChange}
              onKeyDown={handleKeyDown}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default CreateLanguages;
