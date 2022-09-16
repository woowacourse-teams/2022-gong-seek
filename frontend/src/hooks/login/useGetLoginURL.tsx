import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState } from 'recoil';

import { getGithubURL } from '@/api/login';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { errorPortalState } from '@/store/errorPortalState';

const useGetLoginURL = () => {
	const { data, error, isError, isLoading, isSuccess, refetch } = useQuery<
		string,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('github-url', getGithubURL, {
		enabled: false,
		retry: false,
	});
	const [pageLoading, setPageLoading] = useState(false);

	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

	useEffect(() => {
		if (isError) {
			if (!error.response?.data?.errorCode) {
				setErrorPortal({ isOpen: true });
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response?.data.errorCode],
			);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			setPageLoading(true);
			window.location.href = data;
		}
	}, [isSuccess]);

	const handleLoginButtonClick = () => {
		refetch();
	};

	return { isLoading: isLoading && pageLoading, handleLoginButtonClick };
};

export default useGetLoginURL;
