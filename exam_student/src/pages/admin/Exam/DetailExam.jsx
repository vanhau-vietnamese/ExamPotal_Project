import Search from '~/assets/icons/Search';
import DetailExamItem from './DetailExamItem';

export default function DetailExam() {
  return (
    <div className="w-full">
      <div>
        <div className="pb-4 bg-white sticky">
          <label className="sr-only">Search</label>
          <div className="relative m-2 p-2 ">
            <div className="absolute inset-y-0 rtl:inset-r-0 start-0 flex items-center ps-3 pointer-events-none">
              <Search />
            </div>
            <input
              type="text"
              id="table-search"
              className="block pt-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 "
              placeholder="Tìm kiếm..."
            />
          </div>
        </div>
      </div>
      <div className="flex mt-3 ">
        <div className=" w-4/6 max-h-screen overflow-auto rounded-md shadow-sm">
          <DetailExamItem></DetailExamItem>
          <DetailExamItem></DetailExamItem>
        </div>
        <div className="border border-green-200 w-2/6 max-h-[300px] ml-3 rounded-md p-3 shadow-md">
          Thông tin
        </div>
      </div>
    </div>
  );
}
