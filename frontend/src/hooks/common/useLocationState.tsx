import { useLocation } from 'react-router-dom';

const useLocationState = <State = unknown,>() => {
	const { state } = useLocation();

	return state as State;
};

export default useLocationState;
