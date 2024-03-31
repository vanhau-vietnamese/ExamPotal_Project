import PropTypes from 'prop-types';
import { useController } from 'react-hook-form';

export default function FormInput({
  control,
  name,
  title,
  type = 'text',
  required,
  defaultValue,
  error,
  icon,
  className = '',
  ...rest
}) {
  const { field } = useController({
    control,
    name,
    defaultValue: defaultValue || '',
  });
  return (
    <div className="flex flex-col w-full mb-5 gap-y-1">
      <div className="flex items-center justify-between">
        {title && (
          <label htmlFor={name} className="text-sm font-bold text-icon">
            {title}
            {required && <strong className="text-error"> *</strong>}
          </label>
        )}
        {(error || !field.value) && (
          <p className="text-xs font-semibold pointer-events-none text-error">{error}</p>
        )}
      </div>
      <div className="flex items-center">
        {icon && (
          <div className="text-icon px-4 py-2 bg-gray-100 border border-r-0 border-[#d1d2d] rounded-s-md">
            {icon}
          </div>
        )}
        <input
          type={type}
          id={name}
          autoComplete="off"
          className={`flex-1 w-full px-4 py-[10px] border outline-none transition-all placeholder:font-medium disabled:bg-[#eeeff8] disabled:border-transparent text-sm font-semibold ${
            icon ? 'rounded-e-md' : 'rounded-md'
          } ${
            error
              ? 'border-error focus:shadow-invalid'
              : 'border-strike hover:border-green-400 focus:border-secondary'
          }	${className}`}
          {...rest}
          {...field}
        />
      </div>
    </div>
  );
}

FormInput.propTypes = {
  control: PropTypes.object.isRequired,
  name: PropTypes.string.isRequired,
  title: PropTypes.string,
  type: PropTypes.oneOf(['text', 'password', 'email', 'number']),
  required: PropTypes.bool,
  defaultValue: PropTypes.string,
  error: PropTypes.string,
  icon: PropTypes.node,
  className: PropTypes.string,
};
