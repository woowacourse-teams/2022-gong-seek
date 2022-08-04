import { useState } from 'react';
import { useParams } from 'react-router-dom';

import AnonymouseCheckBox from '@/components/common/AnonymousCheckBox/AnonymouseCheckBox';
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
		setTitle,
		categoryOption,
		setCategoryOption,
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
					/>
				</PageLayout>
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
					<PageLayout width="100%" height="fit-content">
						<S.HashTagInput type="text" placeholder="해쉬태그를 입력해주세요" />
					</PageLayout>
				</S.OptionBox>
			</S.SelectorBox>

			<S.Content>
				<ToastUiEditor initContent={''} ref={content} />
			</S.Content>
			<S.SubmitBox>
				<AnonymouseCheckBox setIsAnonymous={setIsAnonymous} />
				<S.SubmitButton
					type="button"
					onClick={() => handleSubmitButtonClick({ title, categoryOption })}
				>
					등록하기
				</S.SubmitButton>
			</S.SubmitBox>
		</S.Container>
	);
};

export default WritingArticles;
