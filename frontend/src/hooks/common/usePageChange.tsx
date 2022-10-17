import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

const usePageChange = (callback: (...args: unknown[]) => void) => {
	const location = useLocation();

	useEffect(() => {
		callback();
	}, [location]);
};

export default usePageChange;
