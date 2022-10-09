import { useEffect } from 'react';

import useInterval from '@/hooks/common/useInterval';

const useAutoSaveTempArticle = (handleTempSavedButtonClick: () => void) => {
	useInterval(handleTempSavedButtonClick, 120000);

	useEffect(() => {
		(() => {
			window.addEventListener('beforeunload', preventRefresh);
		})();

		return () => {
			window.removeEventListener('beforeunload', preventRefresh);
		};
	}, []);

	const preventRefresh = (e: BeforeUnloadEvent) => {
		e.preventDefault();
		e.returnValue = '';
	};
};

export default useAutoSaveTempArticle;
