import PropTypes from 'prop-types';

function Select({ name, label, options, onChange, value, error, required, disabled, ...rest }) {
  return (
    <div className="w-full">
      {label && (
        <label htmlFor={name} className="text-sm font-bold text-icon">
          {label}
          {required && <strong className="text-error"> *</strong>}
        </label>
      )}
      {error && <p className="text-sm font-semibold pointer-events-none text-error">{error}</p>}
      <select
        value={value}
        className={`text-sm flex-1 w-full px-4 py-[10px] font-body border outline-none rounded-md transition-all text-[#3b3e66] cursor-pointer disabled:bg-[#eeeff8] disabled:border-none disabled:cursor-auto font-medium ${
          error
            ? 'border-error focus:shadow-invalid'
            : 'border-[#d1d2d] hover:border-primary60 focus:border-primary40'
        } `}
        disabled={disabled}
        onChange={(e) => onChange(e.target.value)}
        {...rest}
      >
        <option hidden />
        {options.map((item, index) => (
          <option value={item.value} key={index} className="font-medium">
            {item.display}
          </option>
        ))}
      </select>
    </div>
  );
}

export default Select;

Select.propTypes = {
  name: PropTypes.string,
  label: PropTypes.string,
  options: PropTypes.array,
  error: PropTypes.string,
  disabled: PropTypes.bool,
  onChange: PropTypes.func,
  required: PropTypes.bool,
  placeholder: PropTypes.string,
  value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};
