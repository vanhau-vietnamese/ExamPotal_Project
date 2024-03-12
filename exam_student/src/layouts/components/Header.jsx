import { Link } from 'react-router-dom';
import images from '~/assets/images';
import { router } from '~/routes';

function Header() {
  return (
    <header>
      <div className="flex w-screen items-center justify-between mx-auto p-4 sticky top-0 bg-white shadow-xl">
        <a href="https://flowbite.com" className="flex items-center space-x-3 rtl:space-x-reverse">
          <img src={images.icon} className="h-8" alt="Logo" />
          <span className="self-center text-5xl font-semibold whitespace-nowrap dark:text-white">
            Thranuh
          </span>
        </a>

        <div
          id="mega-menu"
          className="items-center justify-between hidden w-full md:flex md:w-auto md:order-1"
        >
          <ul className="flex flex-col mt-4 font-medium text-xl md:flex-row md:mt-0 md:space-x-8 rtl:space-x-reverse">
            <Link
              to={router.dashboard}
              className="block py-2 px-3 text-gray-900 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-600 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-blue-500 md:dark:hover:bg-transparent dark:border-gray-700"
            >
              Trang chủ
            </Link>
            <Link className="block py-2 px-3 text-gray-900 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-600 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-blue-500 md:dark:hover:bg-transparent dark:border-gray-700">
              Thông tin
            </Link>
          </ul>
        </div>
      </div>
    </header>
  );
}

export default Header;
