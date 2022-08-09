import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';

import { getGithubURL } from '@/api/login';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';

const useGetLoginURL = () => {
	const { data, error, isError, isLoading, isSuccess, refetch } = useQuery<
		string,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('github-url', getGithubURL, {
		enabled: false,
	});
	const [pageLoading, setPageLoading] = useState(false);

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
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
