import { Link } from 'react-router-dom';
import Search from '~/assets/icons/Search';
import { TestData } from '~/TestData';
import { router } from '~/routes';

export default function ExamList() {
  return (
    <div className="relative overflow-x-auto shadow-md sm:rounded-lg w-full">
      <div className="pb-4 bg-white">
        <label className="sr-only">Search</label>
        <div className="relative m-2 p-2 ">
          <div className="absolute inset-y-0 rtl:inset-r-0 start-0 flex items-center ps-3 pointer-events-none">
            <Search />
          </div>
          <input
            type="text"
            id="table-search"
            className="block pt-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500"
            placeholder="Tìm kiếm..."
          />
        </div>
        <div className="max-h-[500px] overflow-y-auto">
          <table className="w-full text-sm text-left rtl:text-right text-gray-500">
            <thead className="text-gray-700 uppercase">
              <tr>
                <th scope="col" className="px-6 py-3 w-5/12">
                  Danh sách bài tập
                </th>
                <th scope="col" className="px-6 py-3 w-2/12">
                  Ngày tạo
                </th>
                <th scope="col" className="px-6 py-3 w-2/12">
                  Số câu hỏi
                </th>
                <th scope="col" className="px-6 py-3"></th>
                <th scope="col" className="px-6 py-3"></th>
              </tr>
            </thead>
            <tbody className="overflow-y-scroll">
              {TestData.map((item) => (
                <tr
                  key={item.id}
                  className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50"
                >
                  <td className=" px-6 py-4 break-all">{item.tittel}</td>
                  <td className="px-6 py-4">{item.date}</td>
                  <td className="px-6 py-4">{item.number}</td>
                  <td className="px-6 py-4 text-right">
                    <Link
                      to={router.detailExam}
                      className="font-medium text-yellow-600 dark:text-yellow-500 hover:underline"
                    >
                      Chi tiết
                    </Link>
                  </td>

                  <td className="px-6 py-4 text-right">
                    <Link className="font-medium text-red-600 dark:text-red-500 hover:underline">
                      Xóa
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
