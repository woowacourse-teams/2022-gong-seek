import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';

import { getGithubURL } from '@/api/login';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetLoginURL = () => {
	const { data, error, isLoading, isError, isSuccess, refetch } = useQuery<
		string,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('github-url', getGithubURL, {
		enabled: false,
		retry: false,
	});
	const [pageLoading, setPageLoading] = useState(false);

	useThrowCustomError(isError, error);

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
