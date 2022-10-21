import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import AnonymousCheckBox from '@/components/@common/AnonymousCheckBox/AnonymousCheckBox';
import Card from '@/components/@common/Card/Card';
import Loading from '@/components/@common/Loading/Loading';
import ToastUiEditor from '@/components/@common/ToastUiEditor/ToastUiEditor';
import HashTag from '@/components/hashTag/HashTag/HashTag';
import usePostWritingArticles from '@/hooks/article/usePostWritingArticles';
import useSnackBar from '@/hooks/common/useSnackBar';
import useToastImageConverter from '@/hooks/common/useToastImageConverter';
import useGetTempDetailArticles from '@/hooks/tempArticle/useGetTempDetailArticles';
import usePostTempArticle from '@/hooks/tempArticle/usePostTempArticle';
import * as S from '@/pages/WritingArticles/index.styles';
import { WritingCategoryCardStyle, WritingTitleCardStyle } from '@/styles/cardStyle';

const WritingArticles = ({ tempId = '' }: { tempId?: '' | number }) => {
	const { category } = useParams();
	const [isAnonymous, setIsAnonymous] = useState<boolean>(false);
	const [tempArticleId, setTempArticleId] = useState<'' | number>(tempId);
	const [initContent, setInitContent] = useState<string>('');
	const { showSnackBar } = useSnackBar();

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

	useToastImageConverter(content);

	const {
		saveTempArticleId,
		isSuccess: isTempArticleSavedSuccess,
		isError: isTempArticleSavedError,
		isLoading: isTempArticleSavedLoading,
	} = usePostTempArticle({ tempArticleId, setTempArticleId });

	const {
		data: tempArticleData,
		isLoading: isTempDetailArticleLoading,
		isSuccess: isTempDetailArticleSuccess,
		mutate: getTempDetailArticle,
	} = useGetTempDetailArticles({ tempArticleId });

	useEffect(() => {
		const timerInterval = setInterval(handleClickTemporaryStoreButton, 120000);

		return () => clearInterval(timerInterval);
	}, []);

	useEffect(() => {
		if (typeof tempArticleId === 'number') {
			getTempDetailArticle({ tempArticleId });
		}
	}, []);

	useEffect(() => {
		(() => {
			window.addEventListener('beforeunload', preventRefresh);
		})();

		return () => {
			window.removeEventListener('beforeunload', preventRefresh);
		};
	}, []);

	useEffect(() => {
		if (isTempDetailArticleSuccess && tempArticleData && tempArticleData.data) {
			setTitle(tempArticleData.data.title);
			setHashTags(tempArticleData.data.tag.filter((item) => item !== ''));
			setInitContent(tempArticleData.data.content);
			setIsAnonymous(tempArticleData.data.isAnonymous);
			setCategoryOption(tempArticleData.data.category);
		}
		if (content.current && tempArticleData) {
			content.current.getInstance().setMarkdown(tempArticleData.data.content);
		}
	}, [isTempDetailArticleSuccess]);

	if (isLoading) return <Loading />;

	const handleClickTemporaryStoreButton = () => {
		if (titleInputRef.current && titleInputRef.current.value === '') {
			showSnackBar('제목을 입력해주세요!');
			titleInputRef.current.focus();
			return;
		}
		if (content.current && category && titleInputRef.current) {
			const tempTags = hashTags.filter((item) => item !== '');

			saveTempArticleId({
				title: titleInputRef.current.value,
				category,
				tag: tempTags,
				isAnonymous,
				content: content.current?.getInstance().getMarkdown(),
			});
		}
	};

	const handleChangeCategorySelector = (e: React.ChangeEvent<HTMLSelectElement>) => {
		setCategoryOption(e.target.value);
	};

	const preventRefresh = (e: BeforeUnloadEvent) => {
		e.preventDefault();
		e.returnValue = '';
	};

	return (
		<S.Container>
			<S.SelectorBox>
				<Card {...WritingTitleCardStyle}>
					<S.TitleInput
						type="text"
						placeholder="제목을 입력해주세요"
						value={title}
						onChange={(e) => setTitle(e.target.value)}
						ref={titleInputRef}
					/>
				</Card>
				{!isValidTitleInput && (
					<S.TitleInputErrorMsgBox>제목은 1글자 이상 500자 이하여야 합니다</S.TitleInputErrorMsgBox>
				)}
				<S.OptionBox>
					<Card {...WritingCategoryCardStyle}>
						<S.CategorySelectorBox>
							<S.CategorySelector
								name="writing"
								required
								value={categoryOption}
								onChange={handleChangeCategorySelector}
							>
								<option value="" disabled>
									카테고리를 선택해주세요
								</option>
								<option value="question">질문</option>
								<option value="discussion">토론</option>
							</S.CategorySelector>
							<S.SelectorButton />
						</S.CategorySelectorBox>
					</Card>
					<HashTag hashTags={hashTags} setHashTags={setHashTags} />
				</S.OptionBox>
			</S.SelectorBox>
			<S.TemporaryStoreButtonBox>
				<S.TemporaryStoreButton onClick={handleClickTemporaryStoreButton}>
					임시저장
				</S.TemporaryStoreButton>
			</S.TemporaryStoreButtonBox>
			<S.Content>
				<ToastUiEditor initContent={initContent} ref={content} />
			</S.Content>
			<S.SubmitBox>
				<AnonymousCheckBox setIsAnonymous={setIsAnonymous} />
				<S.SubmitButton
					type="button"
					onClick={() => handleSubmitButtonClick(categoryOption, tempArticleId)}
				>
					등록하기
				</S.SubmitButton>
			</S.SubmitBox>
		</S.Container>
	);
};

export default WritingArticles;
