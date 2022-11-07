import { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import { editUserInfo } from '@/api/user/myPage';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const usePutUserProfile = () => {
	const { data, isSuccess, isError, isLoading, error, mutate } = useMutation<
		AxiosResponse<{ name: string }>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ name: string }
	>(editUserInfo, { retry: 1 });

	useThrowCustomError(isError, error);

	const handleClickUserConfirmButton = ({ name }: { name: string }) => {
		mutate({ name });
	};

	return { handleClickUserConfirmButton, isLoading, isSuccess, data };
};

export default usePutUserProfile;
