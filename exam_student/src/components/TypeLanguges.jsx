import { useState } from 'react';
import Button from './Button';

function TypeLanguges() {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedOption, setSelectedOption] = useState(null);
  const options = ['Giá trị 1', 'Giá trị 2', 'Giá trị 3'];
  const [filteredOptions, setFilteredOptions] = useState(options);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  const handleOptionClick = (option) => {
    setSelectedOption(option);
    setIsOpen(false);
    filterData(option);
  };

  const filterData = (option) => {
    if (option) {
      const filteredData = options.filter((data) => data === option);
      setFilteredOptions(filteredData);
    } else {
      setFilteredOptions(options);
    }
  };

  return (
    <div className="relative inline-block">
      <Button
        onClick={toggleDropdown}
        className="bg-gray-400 py-2 px-4 hover:bg-gray-500 text-white rounded-md max-w-fit h-10 m-5"
      >
        Loại ngôn ngữ
      </Button>
      {isOpen && (
        <div className="origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5">
          <div
            className="py-1"
            role="menu"
            aria-orientation="vertical"
            aria-labelledby="options-menu"
          >
            {filteredOptions.map((option, index) => (
              <a
                key={index}
                href="#"
                className={`block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 ${
                  selectedOption === option ? 'bg-blue-500 text-white' : ''
                }`}
                role="menuitem"
                onClick={() => handleOptionClick(option)}
              >
                {option}
              </a>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default TypeLanguges;
