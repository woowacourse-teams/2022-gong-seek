import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';

import AnonymouseCheckBox from '@/components/common/AnonymousCheckBox/AnonymouseCheckBox';
import HashTag from '@/components/common/HashTag/HashTag';
import Loading from '@/components/common/Loading/Loading';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import ToastUiEditor from '@/pages/WritingArticles/ToastUiEditor/ToastUiEditor';
import usePostWritingArticles from '@/pages/WritingArticles/hooks/usePostWritingArticles';
import * as S from '@/pages/WritingArticles/index.styles';

const WritingArticles = () => {
	const { category } = useParams();
	const [isAnonymous, setIsAnonymous] = useState(false);

	const {
		isLoading,
		content,
		handleSubmitButtonClick,
		title,
		isValidTitleInput,
		setTitle,
		titleInputRef,
		categoryOption,
		setCategoryOption,
		hashTags,
		setHashTags,
	} = usePostWritingArticles({ category, isAnonymous });

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<S.SelectorBox>
				<PageLayout width="100%" height="fit-content">
					<S.TitleInput
						type="text"
						placeholder="제목을 입력해주세요"
						value={title}
						onChange={(e) => setTitle(e.target.value)}
						ref={titleInputRef}
					/>
				</PageLayout>
				{!isValidTitleInput && (
					<S.TitleInputErrorMsgBox>제목은 1글자 이상 500자 이하여야 합니다</S.TitleInputErrorMsgBox>
				)}
				<S.OptionBox>
					<PageLayout width="100%" height="fit-content">
						<S.CategorySelectorBox>
							<S.CategorySelector
								name="writing"
								required
								value={categoryOption}
								onChange={(e) => setCategoryOption(e.target.value)}
							>
								<option value="" disabled>
									카테고리를 선택해주세요
								</option>
								<option value="question">질문</option>
								<option value="discussion">토론</option>
							</S.CategorySelector>
							<S.SelectorButton />
						</S.CategorySelectorBox>
					</PageLayout>
					<HashTag hashTags={hashTags} setHashTags={setHashTags} />
				</S.OptionBox>
			</S.SelectorBox>

			<S.Content>
				<ToastUiEditor initContent={''} ref={content} />
			</S.Content>
			<S.SubmitBox>
				<AnonymouseCheckBox setIsAnonymous={setIsAnonymous} />
				<S.SubmitButton type="button" onClick={() => handleSubmitButtonClick(categoryOption)}>
					등록하기
				</S.SubmitButton>
			</S.SubmitBox>
		</S.Container>
	);
};

export default WritingArticles;
