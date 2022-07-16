import { registerVoteItems } from '@/api/vote';
import AddButton from '@/components/common/AddButton/AddButton';
import Input from '@/components/common/Input/Input';
import styled from '@emotion/styled';
import { AxiosError, AxiosResponse } from 'axios';
import React, { useState } from 'react';
import { useMutation } from 'react-query';
import { useParams } from 'react-router-dom';
import AddedOption from './AddedOption/AddedOption';

const Form = styled.form`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
`;

const OptionInputBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_016};
	align-items: center;
`;

const Content = styled.div`
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_014};
	margin-top: ${({ theme }) => theme.size.SIZE_026};
`;

const SubmitButton = styled.button`
	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	width: 90%;
	font-size: 0.8rem;
	padding: ${({ theme }) => theme.size.SIZE_004};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border-color: transparent;
	cursor: pointer;
	margin-top: ${({ theme }) => theme.size.SIZE_020};

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;

const VoteGenerator = () => {
	const [options, setOptions] = useState<string[]>([]);
	const [input, setInput] = useState('');
	const { articleId } = useParams();
	const { isLoading, mutate, isError, data, isSuccess } = useMutation<
		AxiosResponse<{ articleId: string }>,
		AxiosError,
		{ articleId: string; options: string[] }
	>(registerVoteItems);

	const onSubmitAddOption = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();

		setOptions((prevoptions) => prevoptions.concat(input));
		setInput('');
	};

	const onSubmitVoteForm = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		if (articleId) {
			mutate({ articleId, options });
		}

		if (isSuccess) {
			console.log(data.data.articleId);
		}
	};

	if (isLoading) return <div>로딩중...</div>;

	if (isError) return <div>에러..!</div>;

	return (
		<>
			<Form onSubmit={onSubmitAddOption}>
				<OptionInputBox>
					<Input
						width="70%"
						type="text"
						value={input}
						onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
							setInput(e.target.value);
						}}
					/>
					<AddButton />
				</OptionInputBox>
			</Form>
			<Form onSubmit={onSubmitVoteForm}>
				<Content>
					{options.map((option, idx) => (
						<AddedOption key={idx}>{option}</AddedOption>
					))}
					<SubmitButton>등록하기</SubmitButton>
				</Content>
			</Form>
		</>
	);
};

export default VoteGenerator;
