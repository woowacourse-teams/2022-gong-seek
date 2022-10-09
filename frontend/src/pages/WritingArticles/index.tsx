import { AxiosResponse } from 'axios';
import { useRef, useState } from 'react';
import { useParams } from 'react-router-dom';

import AnonymousCheckBox from '@/components/@common/AnonymousCheckBox/AnonymousCheckBox';
import Card from '@/components/@common/Card/Card';
import Loading from '@/components/@common/Loading/Loading';
import ToastUiEditor from '@/components/@common/ToastUiEditor/ToastUiEditor';
import HashTag from '@/components/hashTag/HashTag/HashTag';
import useAutoSaveTempArticle from '@/hooks/common/useAutoSaveTempArticle';
import useConvertImageUrl from '@/hooks/common/useConvertImageUrl';
import useSnackBar from '@/hooks/common/useSnackBar';
import usePostWritingArticles from '@/hooks/queries/article/usePostWritingArticles';
import useGetTempDetailArticles from '@/hooks/queries/tempArticle/useGetTempDetailArticles';
import usePostTempArticle from '@/hooks/queries/tempArticle/usePostTempArticle';
import * as S from '@/pages/WritingArticles/index.styles';
import { WritingCategoryCardStyle, WritingTitleCardStyle } from '@/styles/cardStyle';
import { TempArticleDetailResponse } from '@/types/articleResponse';
import { validatedTitleInput } from '@/utils/validateInput';
import { Editor } from '@toast-ui/react-editor';

const WritingArticles = ({ tempId = '' }: { tempId?: '' | number }) => {
	const { category } = useParams();
	const [isAnonymous, setIsAnonymous] = useState<boolean>(false);
	const [tempArticleId, setTempArticleId] = useState<'' | number>(tempId);
	const [initContent, setInitContent] = useState<string>('');
	const [title, setTitle] = useState('');
	const [categoryOption, setCategoryOption] = useState<string>(category ? category : '');
	const [isValidTitleInput, setIsValidTitleInput] = useState(true);
	const [hashTags, setHashTags] = useState<string[]>([]);

	const titleInputRef = useRef<HTMLInputElement>(null);
	const content = useRef<Editor | null>(null);

	const { showSnackBar } = useSnackBar();
	const { isLoading, mutate: postMutate } = usePostWritingArticles({ categoryOption });
	const { saveTempArticleId } = usePostTempArticle({ tempArticleId, setTempArticleId });

	const setWritingArticlesInfo = (tempArticleData: AxiosResponse<TempArticleDetailResponse>) => {
		setTitle(tempArticleData.data.title);
		setHashTags(tempArticleData.data.tag.filter((item) => item !== ''));
		setInitContent(tempArticleData.data.content);
		setIsAnonymous(tempArticleData.data.isAnonymous);
		setCategoryOption(tempArticleData.data.category);
	};

	const setEditorContent = (
		content: Editor | null,
		tempArticleData: AxiosResponse<TempArticleDetailResponse>,
	) => {
		if (content && tempArticleData) {
			content.getInstance().setMarkdown(tempArticleData.data.content);
		}
	};

	const handleTempSavedButtonClick = () => {
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

	useGetTempDetailArticles({
		tempArticleId,
		setWritingArticlesInfo,
		setEditorContent,
		content: content.current,
	});

	useConvertImageUrl(content);
	useAutoSaveTempArticle(handleTempSavedButtonClick);

	if (typeof category === 'undefined') {
		throw new Error('카테고리가 존재하지 않습니다.');
	}

	const handleSubmitButtonClick = (categoryOption: string, tempArticleId: number | '') => {
		if (content.current === null) {
			return;
		}

		if (!validatedTitleInput(title)) {
			setIsValidTitleInput(false);

			if (titleInputRef.current !== null) {
				titleInputRef.current.focus();
			}

			return;
		}
		setIsValidTitleInput(true);
		postMutate({
			title: title,
			category: categoryOption,
			content: content.current.getInstance().getMarkdown(),
			tag: hashTags,
			isAnonymous,
			tempArticleId,
		});
	};

	if (isLoading) return <Loading />;

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
					</Card>
					<HashTag hashTags={hashTags} setHashTags={setHashTags} />
				</S.OptionBox>
			</S.SelectorBox>
			<S.TemporaryStoreButtonBox>
				<S.TemporaryStoreButton onClick={handleTempSavedButtonClick}>
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
