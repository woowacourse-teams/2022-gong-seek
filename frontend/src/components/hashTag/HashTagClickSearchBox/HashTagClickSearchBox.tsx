import { useState } from 'react';

import * as S from '@/components/hashTag/HashTagClickSearchBox/HashTagClickSearchBox.styles';

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

	const handleClickHashTagItem = (target: string) => {
		setTargets(
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
			}),
		);
		if (setSelectedTargets) {
			setSelectedTargets(
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
				}),
			);
		}
	};

	const handleClickHashTagButton = () => {
		setIsOpen((prev) => !prev);
	};

	if (targets.length === 0) {
		return <div>해시테그가 존재하지 않습니다</div>;
	}

	return (
		<S.Container>
			<h2 hidden>해시태그들을 보여주는 곳입니다</h2>
			<S.HashTagLists aria-label="검색하고 싶은 해시태그들을 클릭해주세요">
				{targets.slice(0, 5).map((item) => (
					<S.HashTagItem
						key={item.name}
						isChecked={item.isChecked}
						onClick={() => handleClickHashTagItem(item.name)}
					>
						{item.name}
					</S.HashTagItem>
				))}
				{isOpen &&
					targets.slice(5).map((item) => (
						<S.HashTagItem
							key={item.name}
							isChecked={item.isChecked}
							onClick={() => handleClickHashTagItem(item.name)}
						>
							{item.name}
						</S.HashTagItem>
					))}

				{targets.length > 5 && !isOpen && (
					<S.OpenButton
						onClick={() => {
							setIsOpen(!isOpen);
						}}
					/>
				)}
				{isOpen && <S.CloseButton onClick={handleClickHashTagButton} />}
			</S.HashTagLists>
		</S.Container>
	);
};

export default HashTagClickSearchBox;
