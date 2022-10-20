import { useState } from 'react';

import * as S from '@/components/hashTag/HashTagClickSearchBox/HashTagClickSearchBox.styles';
import { HASHTAG_COUNT } from '@/constants';
import { EmptyMessage } from '@/pages/Search/index.styles';

export interface HashTagClickSearchBoxProps {
	targets: { name: string; isChecked: boolean }[];
	setTargets: React.Dispatch<React.SetStateAction<{ name: string; isChecked: boolean }[]>>;
	setSelectedTargets?: React.Dispatch<React.SetStateAction<{ name: string; isChecked: boolean }[]>>;
}

const HashTagClickSearchBox = ({
	targets,
	setTargets,
	setSelectedTargets,
}: HashTagClickSearchBoxProps) => {
	const [isOpen, setIsOpen] = useState(false);

	const filterCheckedHashTags = (target: string) =>
		targets.map((item) => {
			if (item.name === target) {
				return {
					name: item.name,
					isChecked: !item.isChecked,
				};
			}
			return {
				name: item.name,
				isChecked: item.isChecked,
			};
		});

	const handleClickHashTagItem = (target: string) => {
		setTargets(filterCheckedHashTags(target));
		if (setSelectedTargets) {
			setSelectedTargets(filterCheckedHashTags(target));
		}
	};

	const handleClickHashTagButton = () => {
		setIsOpen((prev) => !prev);
	};

	if (targets.length === 0) {
		return <EmptyMessage>해시태그가 존재하지 않습니다</EmptyMessage>;
	}

	return (
		<S.Container>
			<h2 hidden>해시태그들을 보여주는 곳입니다</h2>
			<S.HashTagLists aria-label="검색하고 싶은 해시태그들을 클릭해주세요">
				{targets.slice(0, HASHTAG_COUNT).map((item) => (
					<S.HashTagItem
						key={item.name}
						isChecked={item.isChecked}
						onClick={() => handleClickHashTagItem(item.name)}
					>
						{item.name}
					</S.HashTagItem>
				))}
				{isOpen &&
					targets.slice(HASHTAG_COUNT).map((item) => (
						<S.HashTagItem
							key={item.name}
							isChecked={item.isChecked}
							onClick={() => handleClickHashTagItem(item.name)}
						>
							{item.name}
						</S.HashTagItem>
					))}

				{targets.length > HASHTAG_COUNT && !isOpen && (
					<S.OpenButton onClick={handleClickHashTagButton} />
				)}
				{isOpen && <S.CloseButton onClick={handleClickHashTagButton} />}
			</S.HashTagLists>
		</S.Container>
	);
};

export default HashTagClickSearchBox;
