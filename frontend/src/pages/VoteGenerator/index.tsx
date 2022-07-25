import { registerVoteItems } from '@/api/vote';
import Input from '@/components/common/Input/Input';
import { AxiosError, AxiosResponse } from 'axios';
import React, { useState } from 'react';
import { useMutation } from 'react-query';
import { useParams } from 'react-router-dom';
import AddedOption from '@/pages/VoteGenerator/AddedOption/AddedOption';
import * as S from '@/pages/VoteGenerator/index.styles';
import Loading from '@/components/common/Loading/Loading';

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

	if (isLoading) return <Loading />;

	if (isError) return <div>에러..!</div>;

	return (
		<>
			<S.Form onSubmit={onSubmitAddOption}>
				<S.OptionInputBox>
					<Input
						width="70%"
						type="text"
						value={input}
						onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
							setInput(e.target.value);
						}}
					/>
					<S.AddButtonWrapper>
						<S.AddButton />
					</S.AddButtonWrapper>
				</S.OptionInputBox>
			</S.Form>
			<S.Form onSubmit={onSubmitVoteForm}>
				<S.Content>
					{options.map((option, idx) => (
						<AddedOption key={idx}>{option}</AddedOption>
					))}
					<S.SubmitButton>등록하기</S.SubmitButton>
				</S.Content>
			</S.Form>
		</>
	);
};

export default VoteGenerator;
