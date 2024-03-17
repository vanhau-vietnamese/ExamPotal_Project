export default function DetailExamItem() {
  return (
    <div className="p-3">
      <div className="border border-gray bg-white m-1 rounded-md shadow-md w-80%">
        <h2 className="text-sm font-semibold ml-5 mt-5">Câu 1: Phan Ngọc Như Tranh là ai?</h2>
        <ul className="ml-4 space-y-2">
          <li>
            <label className="flex items-center m-3">
              <div className="flex">
                <span className="font-bold">A. </span>
                <span className="ml-2">Đáp án 1</span>
              </div>
            </label>
            <label className="flex items-center m-3">
              <div className="flex">
                <span className="font-bold">B. </span>
                <span className="ml-2">Đáp án 2</span>
              </div>
            </label>
            <label className="flex items-center m-3">
              <div className="flex">
                <span className="font-bold">C. </span>
                <span className="ml-2">Đáp án 3</span>
              </div>
            </label>
            <label className="flex items-center mb-5">
              <div className="flex">
                <span className="font-bold text-green-700">Đáp án: </span>
              </div>
            </label>
          </li>
        </ul>
      </div>
    </div>
  );
}
