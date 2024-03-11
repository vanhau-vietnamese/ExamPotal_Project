import Button from './Button';

export default function QuestionItem() {
  return (
    <main>
      <div className="p-5">
        <table className="table-auto block">
          <div className="border border-gray m-3 rounded-md shadow-md w-80%">
            <th className="w-3/4">
              Typically animals that are found will be checked for owner identification, including
              checking any ID tags, scanning for microchips, and checking for tattoos.[2] Animals
              may be returned to their owners, or transported to a veterinary clinic or animal
              shelter. Animals held in the shelter can be returned to their owners, adopted,
              released to the wild, held as evidence in a criminal investigation or euthanized.[3]
              Animal control services may be provided by the government or through a contract with a
              humane society or society for the prevention of cruelty to animals. Officers may work
              for, or with, police or sheriff departments, parks and recreation departments, and
              health departments by confining animals or investigating animal bites to humans.
            </th>
            <th className="w-1/4">Đáp án</th>
            <div>
              <Button className="bg-yellow-500 py-2 px-4 hover:bg-yellow-700 text-white rounded-md max-w-fit h-10 m-5 ">
                Chỉnh sửa
              </Button>
              <Button className="bg-red-500 py-2 px-4 hover:bg-red-700 text-white rounded-md w-20 h-10 m-5 ">
                Xóa
              </Button>
            </div>
          </div>
        </table>
      </div>
    </main>
  );
}
