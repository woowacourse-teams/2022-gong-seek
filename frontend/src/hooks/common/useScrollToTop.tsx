import { useEffect } from 'react';

const useScrollToTop = () => {
	useEffect(() => {
		document.documentElement.scrollTo({ top: 0 });
	}, []);
};

export default useScrollToTop;
