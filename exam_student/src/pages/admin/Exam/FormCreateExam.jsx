import { useForm, Controller } from 'react-hook-form';
import { useState } from 'react';
import { Button, FormInput } from '~/components';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import PropTypes from 'prop-types';
import { Question } from '~/layouts/components';
import { Link } from 'react-router-dom';

const FormCreateExam = ({ onClose }) => {
  const { control, handleSubmit } = useForm();
  const [showQuestionList, setShowQuestionList] = useState(false);

  const handleFormSubmit = (data) => {
    // Xử lý logic khi submit form
    console.log(data);
  };

  const handleChooseFromBank = () => {
    // Xử lý khi người dùng chọn câu hỏi từ kho
    // Chuyển hướng hoặc hiển thị modal/form cho người dùng chọn câu hỏi từ kho
    console.log('Chọn câu hỏi từ kho');
    // Cập nhật state để hiển thị danh sách câu hỏi
    setShowQuestionList(true);
  };

  return (
    <div className="flex items-center justify-center pt-6">
      <div className="container mx-auto p-4 bg-slate-100 rounded-md max-w-[1000px]">
        <h3 className="mb-5">Tạo bài tập</h3>
        <form onSubmit={handleSubmit(handleFormSubmit)} className="w-full">
          <div className="flex flex-wrap -mx-3 mb-4">
            <div className="w-full md:w-1/3 px-3 mb-4 md:mb-0">
              <FormInput
                control={control}
                name="examName"
                title="Tên bài tập"
                placeholder="Nhập tên bài tập"
                required
              />
            </div>
            <div className="w-full md:w-1/3 px-3 mb-4 md:mb-0">
              <FormInput
                control={control}
                name="languages"
                title="Ngôn ngữ"
                placeholder="Chọn ngôn ngữ"
                required
              />
            </div>
            <div className="w-full md:w-1/3 px-3 mb-4 md:mb-0">
              <div className="mb-4">
                <label htmlFor="date" className="block text-gray-500 text-sm font-bold mb-2">
                  Hạn nộp
                </label>
                <Controller
                  name="deadline"
                  control={control}
                  render={({ field }) => (
                    <DatePicker
                      {...field}
                      selected={field.value}
                      onChange={(date) => field.onChange(date)}
                      showTimeSelect
                      timeFormat="HH:mm"
                      timeIntervals={15}
                      dateFormat="MMMM d, yyyy h:mm aa"
                      placeholderText="Chọn hạn nộp"
                      className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-400 text-sm"
                    />
                  )}
                />
              </div>
            </div>
            <div>
              <Button type="button" onClick={handleChooseFromBank}>
                Chọn câu hỏi
              </Button>
            </div>
          </div>

          {showQuestionList && (
            <div className="mb-4">
              <div className="flex">
                <Link className="text-lg font-semibold mb-2 mr-5">Danh sách câu hỏi</Link>
                <p> | </p>
                <Link className="text-lg font-semibold mb-2 ml-5">Chi tiết câu hỏi</Link>
              </div>
              <div className="bg-gray-400 w-full rounded-md">
                <div className="max-h-[500px] overflow-y-auto">
                  <Question></Question>
                </div>
              </div>
            </div>
          )}

          <div className="flex justify-center">
            <Button
              type="submit"
              className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 w-32 h-12 text-sm rounded m-1"
            >
              Tạo bài tập
            </Button>
            <Button
              type="button"
              onClick={onClose}
              className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 w-28 h-12 text-sm rounded m-1"
            >
              Thoát
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FormCreateExam;

FormCreateExam.propTypes = {
  onClose: PropTypes.func,
};
